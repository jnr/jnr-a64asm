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

public class REG {
    private REG() {}
    //! @brief Mask for register type.
    public static final int REGTYPE_MASK = 0xF0,
    //! @brief Mask for register code (index).
    REGCODE_MASK = 0x0F,
    //! @brief 64 bit general purpose register type.
    REG_GPR = 0x00,
    //! @brief 32 bit general purpose register type.
    REG_GPT = 0x20,

    REG_X0 = REG_GPR + 0, //argument and result registed from ro to r7
    REG_X1 = REG_GPR + 1,
    REG_X2 = REG_GPR + 2,
    REG_X3 = REG_GPR + 3,
    REG_X4 = REG_GPR + 4,
    REG_X5 = REG_GPR + 5,
    REG_X6 = REG_GPR + 6,
    REG_X7 = REG_GPR + 7,
    REG_X8 = REG_GPR + 8, //indirect result
    REG_X9 = REG_GPR + 9, // x9 to x15 extra spare register
    REG_X10 = REG_GPR + 10,
    REG_X11 = REG_GPR + 11,
    REG_X12 = REG_GPR + 12,
    REG_X13 = REG_GPR + 13,
    REG_X14 = REG_GPR + 14,
    REG_X15 = REG_GPR + 15,
    REG_X16 = REG_GPR + 16,// x16 to x17 intra call registers
    REG_X17 = REG_GPR + 17,
    REG_X18 = REG_GPR + 18,//platform specific (TLS) (instead of R9)
    REG_X19 = REG_GPR + 19,   //X19 to x28 callee saved register
    REG_X20 = REG_GPR + 20,
    REG_X21 = REG_GPR + 21,
    REG_X22 = REG_GPR + 22,
    REG_X23 = REG_GPR + 23,
    REG_X24 = REG_GPR + 24,
    REG_X25 = REG_GPR + 25,
    REG_X26 = REG_GPR + 26,
    REG_X27 = REG_GPR + 27,
    REG_X28 = REG_GPR + 28,
    REG_X29 = REG_GPR + 29, //frame pointer
    REG_X30 = REG_GPR + 30,  // link register
    REG_X31 = REG_GPR + 31,
    //! @brief 32 bit or 64 bit general purpose register type.
    REG_W0 = REG_GPT + 0, //argument and result registed from ro to r7
    REG_W1 = REG_GPT + 1,
    REG_W2 = REG_GPT + 2,
    REG_W3 = REG_GPT + 3,
    REG_W4 = REG_GPT + 4,
    REG_W5 = REG_GPT + 5,
    REG_W6 = REG_GPT + 6,
    REG_W7 = REG_GPT + 7,
    REG_W8 = REG_GPT + 8, //indirect result
    REG_W9 = REG_GPT + 9, // x9 to x15 extra spare register
    REG_W10 = REG_GPT + 10,
    REG_W11 = REG_GPT + 11,
    REG_W12 = REG_GPT + 12,
    REG_W13 = REG_GPT + 13,
    REG_W14 = REG_GPT + 14,
    REG_W15 = REG_GPT + 15,
    REG_W16 = REG_GPT + 16,// x16 to x17 intra call registers
    REG_W17 = REG_GPT + 17,
    REG_W18 = REG_GPT + 18,//platform specific (TLS) (instead of R9)
    REG_W19 = REG_GPT + 19,   //X19 to x28 callee saved register
    REG_W20 = REG_GPT + 20,
    REG_W21 = REG_GPT + 21,
    REG_W22 = REG_GPT + 22,
    REG_W23 = REG_GPT + 23,
    REG_W24 = REG_GPT + 24,
    REG_W25 = REG_GPT + 25,
    REG_W26 = REG_GPT + 26,
    REG_W27 = REG_GPT + 27,
    REG_W28 = REG_GPT + 28,
    REG_W29 = REG_GPT + 29, //frame pointer
    REG_W30 = REG_GPT + 30,  // link register
    NO_REG = 0xFF;
}
