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

/** Operand types that can be encoded in <tt>Op</tt> operand */
public final class OP {
    /** Invalid operand */
    public static final int OP_NONE = 0;

    /** Operand is register. */
    public static final int OP_REG = 1;

    /** Operand is memory. */
    public static final int OP_MEM = 2;

    /** Operand is immediate. */
    public static final int OP_IMM = 3;

    /** Operand is label. */
    public static final int OP_LABEL = 4;

    /** Operand is variable. */
    public static final int OP_EXT = 5;

    /** Operand is variable. */
    public static final int OP_SHIFT = 6;

    /** Operand is variable. */
    public static final int OP_COND = 7;

    /** Operand is variable. */
    public static final int OP_PSTATEFIELD = 8;

    /** Operand is variable. */
    public static final int OP_SYSREG = 9;

    /** Operand is variable. */
    public static final int OP_VAR = 10;

    /** Operand is prefetch operation. */
    public static final int OP_PREFOP = 11;

    /** Operand is  */
    public static final int OP_PREINDEX = 12;

    /** Operand is  */
    public static final int OP_POSTINDEX = 13;

    /** Operand is  offset*/
    public static final int OP_OFFSET = 14;

    /** Operand is  PRFop*/
    public static final int OP_PRFOP = 15;

    private OP() { }
}
