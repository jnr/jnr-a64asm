/*
 * Copyright (C) 2018 ossdev07 ossdev@puresoftware.com
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package jnr.a64asm;

//import static jnr.a64asm.INST_CODE.*;

/**
 * Assembler intrinsics seralizer.
 *
 * SerializerCore is abstract class that is used by @c Assembler and @a Compiler.
 * You probably never use this class directly, instead you use it to serialize
 * intrinsics to @c Assembler or @c Compiler. @c SerializerIntrinsics implements
 * all intruction intrinsics thats used and @c Serializer is public serializer
 * class that should be used (instead of @c SerializerCore or @c SerializerInstrinsics).
 *
 * <b>Note:</b> Use always {@link Serializer} class, this class is only designed to
 * decrease code size when exporting AsmJit library symbols. Some compilers
 * (for example MSVC) are exporting inline symbols when class is declared
 * to export them and {@link Serializer} class contains really huge count of
 * symbols that will be never used (everything is inlined).
 */
public abstract class SerializerCore {

    static final Operand _none = new Operand(OP.OP_NONE, 0) {};

    //! @brief Emits a64/FPU or MM instruction.
    //!
    //! Operands @a o1, @a o2 or @a o3 can be @c NULL if they are not used.
    //!
    //! Hint: Use @c emitA64() helpers to emit instructions.
    abstract void _emita64(INST_CODE code, Operand o1, Operand o2, Operand o3, Operand o4, Operand o5);

    // Helpers to decrease binary code size. These four emit methods are just
    // helpers thats used by serializer. They call _emita64() adding NULLs
    // to first, second and third operand if needed.

    //! @brief Emits instruction with no operand.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code) {
        _emita64(code, _none, _none, _none, _none, _none);
    }

    //! @brief Emits instruction with one operand.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code, Operand o1) {
        _emita64(code, o1, _none, _none, _none, _none);
    }

    //! @brief Emits instruction with two operands.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code, Operand o1, Operand o2) {
        _emita64(code, o1, o2, _none, _none, _none);
    }

    //! @brief Emits instruction with two operands.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code, Operand o1, Operand o2, Operand o3) {
        _emita64(code, o1, o2, o3, _none, _none);
    }

    //! @brief Emits instruction with two operands.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code, Operand o1, Operand o2, Operand o3, Operand o4) {
        _emita64(code, o1, o2, o3, o4, _none);
    }
    //! @brief Emits instruction with three operands.
    //!
    //! Should be use as an alternative to @c _emita64() method.
    void emitA64(INST_CODE code, Operand o1, Operand o2, Operand o3, Operand o4, Operand o5) {
        _emita64(code, o1, o2, o3, o4, o5);
    }

    abstract boolean is64();

};
