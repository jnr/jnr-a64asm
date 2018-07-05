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

public enum CONDITION {
  //! @brief No condition code.
  C_NO_CONDITION  (-1),

  // Condition codes from processor manuals.
  C_EQ      (0x0),
  C_NE      (0x1),
  C_CS      (0x2),
  C_CC      (0x3),
  C_MI      (0x4),
  C_PL      (0x5),
  C_VS      (0x6),
  C_VC      (0x7),
  C_HI      (0x8),
  C_LS      (0x9),
  C_GE      (0x10),
  C_LT      (0x11),
  C_GT      (0x12),
  C_LE      (0x13),
  C_AL      (0x14),
  C_NV      (0x15),
  C_HS      (0x2),
  C_LO      (0x3) ,
  // Simplified condition codes
  C_EQUAL         (0x0),
  C_NOT_EQUAL     (0x1),
  C_ABOVE_EQUAL   (0x2),
  C_BELOW         (0x3),
  C_SIGN          (0x4),
  C_POSITIVE_ZERO (0x5),
  C_OVERFLOW      (0x6),
  C_NO_OVERFLOW   (0x7),
  C_ABOVE         (0x8),
  C_BELOW_EQUAL   (0x9),
  C_GREATER_EQUAL (0xA),
  C_LESS          (0xB),
  C_GREATER       (0xC),
  C_LESS_EQUAL    (0xD),
  C_DEFAULT   (0xE),

  // aliases
  C_ZERO          (0x0),
  C_NOT_ZERO      (0x1),
  C_NEGATIVE      (0x4),
  C_POSITIVE      (0x5);

  private final int value;

  CONDITION(int value) {
      this.value = value;
  }

  public final int value() {
      return this.value;
  }
}
