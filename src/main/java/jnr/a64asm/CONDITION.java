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
