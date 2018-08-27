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
