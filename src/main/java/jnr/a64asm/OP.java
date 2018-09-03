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
