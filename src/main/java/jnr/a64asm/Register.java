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

import static jnr.a64asm.REG.*;
import static jnr.a64asm.SIZE.*;
import static jnr.a64asm.SYSREG_CODE.*;

public  class Register extends BaseReg {
    private static final Register[] gpb = new Register[32];
    private static final Register[] gpw = new Register[32];

    static {
        for (int i = 0; i < 32; ++i) {
            gpb[i] = new Register(REG_GPR | i, SIZE_DWORD);
            gpw[i] = new Register(REG_GPT | i, SIZE_WORD);
        }
    }

    Register(int code, int size) {
        super(code, size);
    }

    public static final Register gpr(int reg) {
        switch (reg & REGTYPE_MASK) {
            case REG_GPR:
                return gpb[reg & REGCODE_MASK];
            case REG_GPT:
                return gpw[reg & REGCODE_MASK];
        }
        throw new IllegalArgumentException("invalid register 0x" + Integer.toHexString(reg));
    }

    private static final Register gpr(Register[] cache, int idx) {
        if (idx >= 0 && idx < 32) {
            return cache[idx];
        }

        throw new IllegalArgumentException("invalid register index " + idx);
    }

    public static final Register gpb(int idx) {
        return gpr(gpb, idx);
    }

    public static final Register gpw(int idx) {
        return gpr(gpw, idx);
    }

}
