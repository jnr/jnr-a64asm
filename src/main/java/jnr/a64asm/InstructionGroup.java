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

public enum InstructionGroup {
    addsub_carry,
    addsub_ext,
    addsub_imm,
    addsub_shift,
    bitfield,
    branch_imm,
    branch_reg,
    compbranch,
    condbranch,
    condcmp_imm,
    condcmp_reg,
    condsel,
    dp_1src,
    dp_2src,
    dp_3src,
    exception,
    extract,
    ldst_imm9,
    ldst_pos,
    ldst_imm9_2reg,
    ldst_pos_2reg,
    ldst_regoff,
    ldst_unpriv,
    ldst_unscaled,
    ldstexcl,
    ldstexcl_op3,
    ldstexcl_op4,
    ldstnapair_offs,
    ldstpair_off,
    ldstpair_indexed,
    loadlit,
    log_imm,
    log_shift,
    movewide,
    pcreladdr,
    ic_system,
    testbranch;

}
