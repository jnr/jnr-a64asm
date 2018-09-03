/*
 * Copyright (C) 2018 Ossdev07
 *
 * This file is part of the JNR project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jnr.a64asm;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import static jnr.a64asm.INST_CODE.*;
import static jnr.a64asm.InstructionGroup.*;
import static jnr.a64asm.OP.*;
import static jnr.a64asm.OperandFlags.*;
import static jnr.a64asm.RELOC_MODE.*;
import static jnr.a64asm.REG.*;
import static jnr.a64asm.Util.*;
import static jnr.a64asm.SIZE.*;
import static jnr.a64asm.PrefOp.*;

/**
 * Low level code generation.
 */
public final class Assembler_A64 extends Serializer {
    private final CodeBuffer _buffer = new CodeBuffer();
    private final List<RelocData> _relocData = new LinkedList<RelocData>();
    private final CpuInfo cpuInfo = CpuInfo.GENERIC;
    private int _properties = 0;

    private final Logger _logger = null;

    private final CPU_A64 cpu;

    @Override
    boolean is64() {
        return cpu == CPU_A64.A64;
    }

    private static final int intValue(boolean b) {
        return b ? 1 : 0;
    }

    public static final CPU_A64 Aarch_64 = CPU_A64.Aarch64;
    public Assembler_A64(CPU_A64 cpu) {
        this.cpu = cpu;
    }

    public final int offset() {
        return _buffer.offset();
    }

    /** Gets the required size of memory required to store all the generated code */
    public final int codeSize() {
        return _buffer.offset();
    }

    //! @brief Set byte at position @a pos.
    public final byte getByteAt(int pos) {
        return _buffer.getByteAt(pos);
    }

    //! @brief Set word at position @a pos.
    public final short getWordAt(int pos) {
        return _buffer.getWordAt(pos);
    }

    //! @brief Set word at position @a pos.
    public final int getDWordAt(int pos) {
        return _buffer.getDWordAt(pos);
    }

    //! @brief Set word at position @a pos.
    public final long getQWordAt(int pos) {
        return _buffer.getQWordAt(pos);
    }

    //! @brief Set byte at position @a pos.
    public final void setByteAt(int pos, byte x) {
        _buffer.setByteAt(pos, x);
    }

    //! @brief Set word at position @a pos.
    public final void setWordAt(int pos, short x) {
        _buffer.setWordAt(pos, x);
    }

    //! @brief Set word at position @a pos.
    public final void setDWordAt(int pos, int x) {
        _buffer.setDWordAt(pos, x);
    }

    //! @brief Set word at position @a pos.
    public final void setQWordAt(int pos, long x) {
        _buffer.setQWordAt(pos, x);
    }

    //! @brief Set word at position @a pos.
    public final int getInt32At(int pos) {
        return (int) _buffer.getDWordAt(pos);
    }

    //! @brief Set int32 at position @a pos.
    public final void setInt32At(int pos, long x) {
        _buffer.setDWordAt(pos, (int) x);
    }

    public final void setVarAt(int pos, long i, boolean isUnsigned, int size) {
        switch (size) {
            case 1:
                setByteAt(pos, (byte) i);
                break;
            case 2:
                setWordAt(pos, (short) i);
                break;
            case 4:
                setDWordAt(pos, (int) i);
                break;
            case 8:
                setQWordAt(pos, i);
            default:
                throw new IllegalArgumentException("invalid size");
        }
    }

    /** Emit Byte to internal buffer. */
    final void _emitByte(int  x) {
        _buffer.emitByte((byte) x);
    }

    /** Emit Word (2 bytes) to internal buffer. */
    final void _emitWord(int x) {
        _buffer.emitWord((short) x);
    }

    /** Emit DWord (4 bytes) to internal buffer. */
    final void _emitDWord(int x) {
        _buffer.emitDWord(x);
    }

    /** Emit QWord (8 bytes) to internal buffer. */
    final void _emitQWord(long x) {
        _buffer.emitQWord(x);
    }

    /** Emit Int32 (4 bytes) to internal buffer. */
    final void _emitInt32(int x) {
        _buffer.emitDWord(x);
    }

    /** Emit system signed integer (4 or 8 bytes) to internal buffer. */
    final void _emitSysInt(long x) {
        if (is64()) {
            _buffer.emitQWord(x);
        } else {
            _buffer.emitDWord((int) x);
        }
    }

    //! @brief Emit single @a opCode without operands.
    final void _emitOpCode(int opCode) {
        if ((opCode & 0xFF000000) != 0) {
            _emitByte((byte) ((opCode & 0xFF000000) >> 24));
        }
        if ((opCode & 0x00FF0000) != 0) {
            _emitByte((byte) ((opCode & 0x00FF0000) >> 16));
        }
        if ((opCode & 0x0000FF00) != 0) {
            _emitByte((byte) ((opCode & 0x0000FF00) >> 8));
        }
        _emitByte((byte) (opCode & 0x000000FF));
    }

    void _emitImmediate(Immediate imm, int size) {
        switch (size) {
            case 1:
                _emitByte(imm.byteValue());
                break;
            case 2:
                _emitWord(imm.shortValue());
                break;
            case 4:
                _emitDWord(imm.intValue());
                break;
            case 8:
                if (!is64()) {
                    throw new IllegalArgumentException("64 bit immediate values not supported for 32bit");
                }
                _emitQWord(imm.longValue());
                break;
            default:
                throw new IllegalArgumentException("invalid immediate operand size");
        }
    }

    void _emita64(INST_CODE code, Operand o1, Operand o2, Operand o3, Operand o4, Operand o5) {
        InstructionDescription id = InstructionDescription.find(code);
        switch(id.group){
            case addsub_carry:
            case addsub_ext:
            {
                int inst_to_emit = 0;
                if ((o1.isReg() && o2.isReg() && o3.isReg()) || (o4 != null && o4.isExtend())){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = (Register) o3;
                    Ext extV = null;
                    if(o4 != _none && o4.isExtend())
                        extV = (Ext) o4;
                    if(o1.size() == SIZE_DWORD){
                            inst_to_emit |= 1 << 31;
                    }
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (regN.code &  0b11111) << 5;
                    inst_to_emit |= (regM.code &  0b11111) << 16;
                    if((id.group == addsub_ext) && (extV != null)){
                        inst_to_emit |= (extV.value() & 0b111) << 10;
                        inst_to_emit |= (extV.type() & 0b111) << 13;
                    }
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case addsub_imm:
            case addsub_shift:
            {
                int inst_to_emit = 0;
                if ((o1 != _none && o1.isReg()) && (o2 != _none && o2.isReg())){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Immediate val = null ;
                    if(o3 != _none  && o3.isImm())
                        val = (Immediate) o3;
                    Shift sft = null;
                    if(o4 != _none)
                        sft = (Shift) o4;
                    Register regM = null;
                    if(o3 != _none && o3.isReg())
                        regM = (Register) o3;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 1 << 31;
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (regN.code &  0b11111) << 16;
                    if(id.group == addsub_shift){
                        if(regM != null)
                            inst_to_emit |= (regM.code &  0b11111) << 16;
                        if (sft != null)
                            inst_to_emit |= (sft.value() &  0b111111) << 10;
                    }
                    else {
                        if (val != null)
                            inst_to_emit |= (val.value() &  0xfff) << 10;
                    }
                    if(sft != null)
                        inst_to_emit |= (sft.type() & 0b11) << 22;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case bitfield:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Immediate val1 = null ;
                    if(o3.isImm())
                        val1 = (Immediate) o3;
                    Immediate val2 = null;
                    if(o4.isImm())
                        val2 = (Immediate) o4;
                    if(o1.size() == SIZE_DWORD){
                        inst_to_emit |= 1 << 31;
                        inst_to_emit |= 1 << 22;
                    }
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (val1 != null)
                        inst_to_emit |= (val1.value() &  0b111111) << 10;
                    if (val2 != null)
                        inst_to_emit |= (val2.value() &  0b111111) << 16;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case branch_imm:
            {
                int inst_to_emit = 0;
                if (o1 != _none){
                    Immediate mem = (Immediate) o1;
                    /*<label> Is the program label to be unconditionally branched to. Its offset from the address of this instruction,
                    in the range +/-128MB, is encoded as "imm26" times 4.*/
                    _emitJmpOrCallReloc(branch_imm, mem.value());
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case branch_reg:
            {
                int inst_to_emit = 0;
                Register regN = null;
                if(o1 != _none && o1 != null && o1.isReg()) /*because at jnr-ffi level this is called like this a.ret((Register)null);*/
                regN = (Register) o1;
                if (regN != null)
                    inst_to_emit |= (regN.code &  0b11111) << 5;
                if(regN == null && id.code == INST_RET_BRANCH_REG)
                    inst_to_emit |= 0b11110 << 5;   /*in the case of RET default location is X30*/
                inst_to_emit |= id.opcode;
                _emitInt32(inst_to_emit);
                break;
            }
            case compbranch:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isLabel()){
                    Register regD = (Register) o1;
                    Label labl = (Label) o2;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 1 << 31;
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (labl.position() & 0x7ffff) << 5;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case condbranch:
            {
                int inst_to_emit = 0;
                if (o1.isImm()){
                    Immediate imm19 = (Immediate) o1;
                    if (imm19 != null)
                        inst_to_emit |= (imm19.value() & 0x7ffff) << 5;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case condcmp_imm:
            case condcmp_reg:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isImm()){
                    Register regD = (Register) o1;
                    Register regM = (Register) o2;
                    Immediate val = (Immediate) o2;
                    Immediate nzcv = (Immediate) o3;
                    Conditions cond = (Conditions) o4;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    if (id.group == condcmp_reg && regM != null)
                        inst_to_emit |= (regM.code &  0b11111) << 16;
                    else{
                        if (val != null)
                            inst_to_emit |= (val.value() & 0b11111) << 16;
                    }
                    if (nzcv != null)
                        inst_to_emit |= nzcv.value() & 0b1111;
                    if (cond != null)
                        inst_to_emit |= (cond.value() & 0b1111) << 12;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case condsel:
            {
                int inst_to_emit = 0;
                if (o1.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = (Register) o3;
                    Conditions cond0 = (Conditions) o4;
                    Conditions cond1 = (Conditions) o3;
                    Conditions cond2 = (Conditions) o2;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                        if(o4.isCond() && ((cond0.value() & 0b1110) != 0b1110)){
                            inst_to_emit |= (cond0.value() ^ 0b1) << 12;
                            inst_to_emit |= (regN.code &  0b11111) << 5;
                            inst_to_emit |= (regM.code &  0b11111) << 16;
                        }
                        else if(o3.isCond() && ((cond1.value() & 0b1110) != 0b1110)){
                            inst_to_emit |= (cond1.value() ^ 0b1) << 12;
                            inst_to_emit |= (regN.code &  0b11111) << 5;
                            inst_to_emit |= (0b11111) << 16;    /*regM is 0b11111 if not explicitly mentioned in ASM inst*/
                        }
                        else if(o2.isCond() && ((cond2.value() & 0b1110) != 0b1110)){
                            inst_to_emit |= (cond1.value() ^ 0b1) << 12;
                            inst_to_emit |= (0b11111) << 5;
                            inst_to_emit |= (0b11111) << 16;    /*regM&N is 0b11111 if not explicitly mentioned in ASM inst*/
                        }
                        inst_to_emit |= id.opcode;
                        _emitInt32(inst_to_emit);
                    }
                    else
                        throw new IllegalArgumentException("illegal arguments");
                    break;
            }
            case dp_1src:
            case dp_2src:
            case dp_3src:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = (Register) o3;
                    Register regA = (Register) o4;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (regM != null && (id.group == dp_2src || id.group == dp_3src))
                        inst_to_emit |= (regM.code &  0b11111) << 16;
                    if (regA != null && id.group == dp_3src)
                        inst_to_emit |= (regA.code &  0b11111) << 10;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case exception:
            {
                int inst_to_emit = 0;
                if (o1.isImm()){
                    Immediate imm16 = (Immediate) o1;
                    if(imm16 != null)
                        inst_to_emit |= (imm16.value() & 0xffff) << 5;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case extract:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = (Register) o3;
                    Immediate imm6 = (Immediate) o4;
                    Immediate imm6_1 = (Immediate) o3;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (o3.isReg() && regM != null){
                        inst_to_emit |= (regM.code &  0b11111) << 16;
                        inst_to_emit |= (imm6.value() & 0b111111) << 10;
                    }
                    else if (o3.isImm() && imm6_1 != null ){
                        inst_to_emit |= (imm6_1.value() & 0b111111) << 10;
                        inst_to_emit |= (regN.code &  0b11111) << 16;   /*in the case of ROR Rn and Rm will hold same value*/
                    }
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ldst_imm9:
            case ldst_pos:
            {
                int inst_to_emit = 0;
                if (o1.isReg() || id.code == INST_PRFM_LDST_POS__IMMEDIATE){
                    Register regD = null;
                    PRFOP_ENUM pfrop = null;
                    if(id.code == INST_PRFM_LDST_POS__IMMEDIATE && o1 != null)
                        pfrop = (PRFOP_ENUM)o1;
                    else if(o1 != _none)
                        regD = (Register) o1;
                    Register regN = null;
                    Post_index postindex = null;
                    Pre_index preindex = null;
                    Offset offset = null;
                    Immediate imm9 = null;
                    Immediate imm12 = null;
                    if(o2 != _none && o2.isReg())
                        regN = (Register) o2;
                    else if((o2 != _none) && (o2.isPreIndex() || o2.isPostIndex() || o2.isOffset())){
                        if(o2.isPreIndex()) {
                            preindex = (Pre_index)o2;
                            regN = preindex.getRegister();
                            imm9 = preindex.getPreIndex();
                        }
                        else if(o2.isPostIndex()){
                            postindex = (Post_index)o2;
                            regN = postindex.getRegister();
                            imm9 = postindex.getPostIndex();
                        }
                        else{
                            offset = (Offset)o2;
                            regN = offset.getRegister();
                            imm12 = offset.getOffset();
                        }
                    }
                    if(o3 != _none && id.group == ldst_imm9 && !(o2.isPreIndex() || o2.isPostIndex()))
                        imm9 = (Immediate)o3;
                    if(o3 != _none && id.group == ldst_pos && !(o2.isPreIndex() || o2.isPostIndex()))
                        imm12 = (Immediate) o3;
                    if(o1.size() == SIZE_DWORD && id.code != INST_PRFM_LDST_POS__IMMEDIATE && id.code != INST_LDRSW_IMM_OFF && id.code != INST_LDRH_IMM_OFF && id.code != INST_LDRSH_IMM_OFF && id.code != INST_LDRB_IMM_OFF && id.code != INST_LDRSB_IMM_OFF)
                        inst_to_emit |= 1 << 30;
                    if(o1.size() == SIZE_WORD && id.code != INST_LDRB_IMM_OFF  && (id.code == INST_LDRSH_IMM_OFF || id.code == INST_LDRSB_IMM_OFF) )
                        inst_to_emit |= 1 << 22;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    else if (pfrop != null)
                        inst_to_emit |= pfrop.intValue() & 0b11111; /*in case of prfm inst*/
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (id.group == ldst_imm9)
                        inst_to_emit |= (imm9.value() & 0x1ff) << 12;
                    else if(id.group == ldst_pos && (id.code == INST_LDRB_IMM_OFF || id.code == INST_LDRSB_IMM_OFF))
                        inst_to_emit |= (imm12.value() & 0xfff) << 10;
                    else if(id.group == ldst_pos && (id.code == INST_LDRH_IMM_OFF || id.code == INST_LDRSH_IMM_OFF))
                        inst_to_emit |= (imm12.value()>>1 & 0xfff) << 10;
                    else if(id.group == ldst_pos)
                        inst_to_emit |= (imm12.value()>>((id.code == INST_LDRSW_IMM_OFF)?(2):(3)) & 0xfff) << 10;   /*<pimm> Is the optional positive immediate byte offset, a multiple of 8 in the range 0 to 32760, defaulting to 0 and encoded in the "imm12" field as <pimm>/8.*/
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ldst_imm9_2reg:
            case ldst_pos_2reg:
            {
                int inst_to_emit = 0;
                if (o1.isReg()){
                    Register regT = (Register) o1;
                    Register regT2 = (Register) o2;
                    Post_index postindex = null;
                    Pre_index preindex = null;
                    Offset offset = null;
                    Immediate imm7 = null;
                    Register regN = null;
                    if(o3 != _none && o3.isPreIndex() || o3.isPostIndex() || o3.isOffset()){
                        if(o3.isPreIndex()) {
                            preindex = (Pre_index)o3;
                            regN = preindex.getRegister();
                            imm7 = preindex.getPreIndex();
                        }
                        else if(o3.isPostIndex()){
                            postindex = (Post_index)o3;
                            regN = postindex.getRegister();
                            imm7 = postindex.getPostIndex();
                        }
                        else{
                            offset = (Offset)o3;
                            regN = offset.getRegister();
                            imm7 = offset.getOffset();
                        }
                    }
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 1 << 31;
                    if (regT != null)
                        inst_to_emit |= regT.code & 0b11111;
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (regT2 != null)
                        inst_to_emit |= (regT2.code &  0b11111) << 10;
                    /*<imm> For the 32-bit variant: is the optional signed immediate byte offset, a multiple of 4 in the range -256
                    to 252, defaulting to 0 and encoded in the "imm7" field as <imm>/4.
                    <imm> For the 64-bit variant: is the optional signed immediate byte offset, a multiple of 8 in the range -512
                    to 504, defaulting to 0 and encoded in the "imm7" field as <imm>/8. */
                    if(imm7 != null)
                        inst_to_emit |= ((imm7.value()>>((o1.size() == SIZE_DWORD)?(3):(2)))  & 0b1111111) << 15;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ldst_regoff:
            {
                int inst_to_emit = 0;
                if ((o1.isReg() && o2.isReg())  || id.code == INST_PRFM_LDST_REGOFF__REGISTER){
                    Register regD = null;
                    PRFOP_ENUM pfrop = null;
                    if(id.code == INST_PRFM_LDST_REGOFF__REGISTER)
                        pfrop = (PRFOP_ENUM) o1;
                    else
                        regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = (Register) o3;
                    Ext extnd = (Ext) o3;
                    if(o1.size() == SIZE_DWORD && id.code != INST_PRFM_LDST_REGOFF__REGISTER)
                        inst_to_emit |= 1 << 30;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    else
                        inst_to_emit |= pfrop.intValue() & 0b11111;
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (regM != null)
                        inst_to_emit |= (regM.code &  0b11111) << 16;
                    if (o4 != null && o4.isExtend()){
                        inst_to_emit |= (extnd.value() == 3 || extnd.value() == 2) ? (0b1 << 12) : (0);
                        inst_to_emit |= (extnd.type() & 0b111) << 13;
                    }
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ldst_unpriv:
            case ldst_unscaled:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg() || id.code == INST_PRFUM_LDST_UNSCALED){
                    Register regD = null;
                    PRFOP_ENUM pfrop = null;
                    if(id.code == INST_PRFUM_LDST_UNSCALED)
                        pfrop = (PRFOP_ENUM) o1;
                    else
                        regD = (Register) o1;
                    Register regN = (Register) o2;
                    Immediate imm9 = (Immediate) o3;
                    if (regD != null)
                        inst_to_emit |= regD.code & 0b11111;
                    else
                        inst_to_emit |= pfrop.intValue() & 0b11111;
                    if (regN != null)
                        inst_to_emit |= (regN.code &  0b11111) << 5;
                    if (o3 != null && o3.isImm())
                        inst_to_emit |= (imm9.value() &  0x1ff) << 12;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ldstexcl:
            case ldstexcl_op3:
            {
                int inst_to_emit = 0;
                Register regD = (Register) o1;
                Register regN = (Register) o2;
                Register regM = null;
                Offset offset = null;
                if(o3 != null && o3.isReg())
                    regM = (Register) o3;
                else if (o3 != null && o3.isOffset()){
                    offset = (Offset) o3;
                    regM = offset.getRegister();
                }
                inst_to_emit |= (regD.code & 0b11111) << 16;
                inst_to_emit |= regN.code &  0b11111;
                if(id.group == ldstexcl_op3 && regM != null)
                    inst_to_emit |= (regM.code &  0b11111) << 5;
                inst_to_emit |= id.opcode;
                _emitInt32(inst_to_emit);
                break;
            }
            case ldstexcl_op4:
            {
                int inst_to_emit = 0;
                Register regS = (Register) o1;
                Register regt = (Register) o2;
                Register regt2 = (Register) o3;
                Register regN = (Register) o3;
                inst_to_emit |= (regS.code & 0b11111) << 16;
                inst_to_emit |= regt.code &  0b11111;
                inst_to_emit |= (regt2.code &  0b11111) << 10;
                inst_to_emit |= (regN.code &  0b11111) << 5;
                inst_to_emit |= id.opcode;
                _emitInt32(inst_to_emit);
                break;
            }
            case ldstnapair_offs:
            case ldstpair_off:
            case ldstpair_indexed:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = null;
                    Pre_index preindex = null;
                    Post_index posindex = null;
                    Immediate imm7 = null;
                    if (o3.isReg())
                        regM = (Register) o3;
                    else if (o3.isPostIndex()){
                        posindex = (Post_index) o3;
                        regM = posindex.getRegister();
                        imm7 = posindex.getPostIndex();
                    }
                    else if(o3.isPreIndex()){
                        preindex = (Pre_index) o3;
                        regM = preindex.getRegister();
                        imm7 = preindex.getPreIndex();
                    }
                    if(o4 != _none && o4.isImm())
                        imm7 = (Immediate) o4;
                    if (id.group == ldstexcl || id.group == ldstnapair_offs){
                        if(o1.size() == SIZE_DWORD )
                            inst_to_emit |= 0b1 << 30;
                        else if(o1.size() == SIZE_QWORD )
                            inst_to_emit |= 0b1 << 31;
                    }
                    else{
                        if(o1.size() == SIZE_DWORD)
                            inst_to_emit |= 0b1 << 31;
                    }
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (regN.code &  0b11111) << 10;
                    inst_to_emit |= (regM.code &  0b11111) << 5;
                    /*<imm> For the 32-bit variant: is the optional signed immediate byte offset, a multiple of 4 in the range -256
                    to 252, defaulting to 0 and encoded in the "imm7" field as <imm>/4.
                    <imm> For the 64-bit variant: is the optional signed immediate byte offset, a multiple of 8 in the range -512
                    to 504, defaulting to 0 and encoded in the "imm7" field as <imm>/8. */
                    if(imm7 != null)
                        inst_to_emit |= ((imm7.value()>>((o1.size() == SIZE_DWORD)?(3):(2)))  & 0b1111111) << 15;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case loadlit:
            {
                int inst_to_emit = 0;
                if (o1.isReg() || id.code == INST_PRFM_LOADLIT__LITERAL){
                    Register regD = null;
                    PRFOP_ENUM prfop = null;
                    if(id.code == INST_PRFM_LOADLIT__LITERAL)
                        prfop = (PRFOP_ENUM)o1;
                    else
                        regD = (Register) o1;
                    Immediate lbl = (Immediate) o2;
                    if(o1.size() == SIZE_DWORD && id.code != INST_PRFM_LOADLIT__LITERAL)
                        inst_to_emit |= 0b1 << 30;
                    if(id.code == INST_PRFM_LOADLIT__LITERAL)
                        inst_to_emit |= prfop.intValue() & 0b11111;
                    else
                        inst_to_emit |= regD.code & 0b11111;
                    /*<label> Is the program label from which the data is to be loaded. Its offset from the address of this
                    instruction, in the range +/-1MB, is encoded as "imm19" times 4.*/
                    inst_to_emit |= (lbl.value()>>2 & 0x7fff) << 5;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case log_imm:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg() && o3.isImm()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Immediate imm = (Immediate) o3;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 0b1 << 31;
                    else
                        inst_to_emit &= ~(0b1 << 22);
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (regN.code & 0b11111) << 5;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= (imm.value() & 0x1fff) << 10;
                    else
                        inst_to_emit |= (imm.value() & 0xfff) << 10;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case log_shift:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isReg()){
                    Register regD = (Register) o1;
                    Register regN = (Register) o2;
                    Register regM = null;
                    if(o3 != _none) regM = (Register) o3;
                    Shift sft = null;
                    if(o4 != _none)
                        sft = (Shift) o4;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 0b1 << 31;
                    inst_to_emit |= regD.code & 0b11111;
                    if(id.code == INST_MOV_LOG_SHIFT){
                        inst_to_emit |= (regN.code & 0b11111) << 5;
                    }
                    else{
                        inst_to_emit |= (regN.code & 0b11111) << 5;
                        inst_to_emit |= (regM.code & 0b11111) << 16;
                    }
                    if(sft != null){
                        inst_to_emit |= (sft.value() & 0b111111) << 10;
                        inst_to_emit |= (sft.type() & 0b11) << 22;
                    }
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case movewide:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isImm()){
                    Register regD = (Register) o1;
                    Immediate imm16 = (Immediate) o2;
                    Shift sft = null;
                    if(o3 != _none)
                        sft = (Shift) o3;
                    if(o1.size() == SIZE_DWORD)
                        inst_to_emit |= 0b1 << 31;
                    inst_to_emit |= regD.code & 0b11111;
                    inst_to_emit |= (imm16.value() & 0xffff) << 5;
                    if(sft != null && sft.value()%16 == 0 && sft.value() < 49 ) /*shift val can be 0, 16, 32, 48 only*/
                        inst_to_emit |= (sft.value()>>4 & 0b11) << 21;  // type is LSL only,
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case pcreladdr:
            {
                int inst_to_emit = 0;
                if (o1.isReg() && o2.isImm()){
                    Register regD = (Register) o1;
                    Immediate imm16 = (Immediate) o2;
                    inst_to_emit |= regD.code & 0b11111;
                    /* the program label whose 4KB page address is to be calculated. Its offset from the page address of
                    this instruction, in the range +/-4GB, is encoded as "immhi:immlo" times 4096.*/
                    long imm = imm16.value() >> 12;
                    inst_to_emit |= (imm>>2 & 0x7ffff) << 5;
                    inst_to_emit |= (imm & 0b11) << 29;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }
            case ic_system:
            {
                int inst_to_emit = 0;
                if(id.code == INST_NOP_IC_SYSTEM || id.code == INST_YIELD_IC_SYSTEM || id.code == INST_WFE_IC_SYSTEM || id.code == INST_WFI_IC_SYSTEM || id.code == INST_SEV_IC_SYSTEM || id.code == INST_SEVL_IC_SYSTEM )
                    inst_to_emit |= id.opcode;
                else if(id.code == INST_HINT_IC_SYSTEM || id.code == INST_CLREX_IC_SYSTEM || id.code == INST_DSB_IC_SYSTEM || id.code == INST_DMB_IC_SYSTEM || id.code == INST_ISB_IC_SYSTEM){
                    Immediate imm = (Immediate) o1;
                    if(id.code == INST_HINT_IC_SYSTEM)
                        inst_to_emit |= (imm.value() & 0b1111111) << 5; /*<imm> Is a 7-bit unsigned immediate, in the range 0 to 127, encoded in "CRm:op2".*/
                    else
                        inst_to_emit |= (imm.value() & 0b1111) << 8;
                }
                else if(id.code == INST_MSR_IC_SYSTEM_X){
                    SysRegister sysrt = (SysRegister) o1;
                    Register rt = (Register) o2;
                    SysRegDescription sysregid = SysRegDescription.find(sysrt.getEnum());
                    inst_to_emit |= (sysregid.reg_code & 0xffff) << 5;
                    inst_to_emit |= rt.code & 0b11111;
                }
                else if(id.code == INST_SYS_IC_SYSTEM || id.code == INST_SYSL_IC_SYSTEM){
                    Immediate imm3_op1 = (Immediate) o1;
                    Register Rt = (Register) o1;
                    Register cRn = (Register) o2;
                    Immediate imm3_op1_2 = (Immediate) o2;
                    Register cRm = (Register) o3;
                    Register cRn_2 = (Register) o3;
                    Immediate imm3_op2_4 = (Immediate) o4;
                    Register cRm_2 = (Register) o4;
                    Register rt = null;
                    Immediate imm3_op2_5_2 = null;
                    if(o5 != null){
                        rt = (Register) o5;
                        imm3_op2_5_2 = (Immediate) o5;
                    }
                    inst_to_emit |= (((id.code == INST_SYS_IC_SYSTEM) ? (imm3_op1.value())   : (imm3_op1_2.value()))   & 0b111)  << 16;
                    inst_to_emit |= (((id.code == INST_SYS_IC_SYSTEM) ? (cRn.code())         : (cRn_2.code()))         & 0b1111) << 12;
                    inst_to_emit |= (((id.code == INST_SYS_IC_SYSTEM) ? (cRm.code())         : (cRm_2.code()))         & 0b1111) << 8;
                    inst_to_emit |= (((id.code == INST_SYS_IC_SYSTEM) ? (imm3_op2_4.value()) : (imm3_op2_5_2.value())) & 0b111)  << 5;
                    if(id.code == INST_SYS_IC_SYSTEM)
                        if(rt != null)
                            inst_to_emit |= rt.code() & 0b11111;
                        else
                            inst_to_emit |= 0b11111;  /*<Xt> Is the 64-bit name of the optional general-purpose source register, defaulting to '11111', encoded in the "Rt" field.*/
                    else
                        inst_to_emit |= Rt.code() & 0b11111;
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                inst_to_emit |= id.opcode;
                _emitInt32(inst_to_emit);
                break;
            }
            case testbranch:
            {
                int inst_to_emit = 0;
                if (o1.isReg()){
                    Register regD = (Register) o1;
                    Immediate imm = (Immediate) o2;
                    Immediate lbl_imm14  = (Immediate) o3;
                    inst_to_emit |= regD.code & 0b11111;
                    /* <imm> Is the bit number to be tested, in the range 0 to 63, encoded in "b5:b40".*/
                    inst_to_emit |= (imm.value() & 0b11111) << 19;
                    inst_to_emit |= (imm.value()>>5 & 0b1) << 31;
                    inst_to_emit |= (lbl_imm14.value() & 0x3fff) << 5;
                    inst_to_emit |= id.opcode;
                    _emitInt32(inst_to_emit);
                }
                else
                    throw new IllegalArgumentException("illegal arguments");
                break;
            }   /*END of case testbranch:*/
        }   /*END of switch(id.group)*/
    }   /*END of void _emita64(INST_CODE code, Operand o1, Operand o2, Operand o3, Operand o4, Operand o5)*/

    void _emitJmpOrCallReloc(InstructionGroup instruction, long target) {
        RelocData rd = new RelocData(RelocData.Type.ABSOLUTE_TO_RELATIVE_TRAMPOLINE, 4, offset(), target);
        _relocData.add(rd);
        // Emit dummy 32-bit integer (will be overwritten by relocCode()).
        _emitInt32(0);
    }

    public void relocCode(ByteBuffer buffer, long address) {
        // Copy code to virtual memory (this is a given _dst pointer).
        int csize = codeSize();

        // We are copying exactly size of generated code. Extra code for trampolines
        // is generated on-the-fly by relocator (this code not exists at now).
        _buffer.copyTo(buffer);

        // Relocate recorded locations.
        for (RelocData r : _relocData) {
            long val;

            // Be sure that reloc data structure is correct.
            assert ((r.offset + r.size) <= csize);

            switch (r.type) {
                case ABSOLUTE_TO_ABSOLUTE:
                    val = r.destination;
                    break;
                case RELATIVE_TO_ABSOLUTE:
                    val = address + r.destination;
                    break;
                case ABSOLUTE_TO_RELATIVE:
                case ABSOLUTE_TO_RELATIVE_TRAMPOLINE:
                    if (  (r.destination-(address+r.offset)) > (128*1024*1024) )
                        System.out.println("IMPOSSIBLE JUMP : ADDRESS AHEAD OF RANGE of 128MB");
                    if (  (r.destination-(address+r.offset)) < -(128*1024*1024) )
                        System.out.println("IMPOSSIBLE JUMP : ADDRESS BELOW OF RANGE of 128MB");
                    val = ((r.destination)-(address+r.offset))/4;
                    break;
                default:
                    throw new IllegalStateException("invalid relocation type");
            }

            switch (r.size) {
                case 4:
                    val &= (int)0x3FFFFFF;  /*mask for imm27  val*/
                    val |= 0x94000000;  /*opcode for BL*/
                    buffer.putInt(r.offset, (int) val);
                    break;
                case 8:
                    buffer.putLong(r.offset, val);
                    break;
                default:
                    throw new IllegalStateException("invalid relocation size");
            }
        }
    }

}
