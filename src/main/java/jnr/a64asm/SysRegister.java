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

import static jnr.a64asm.SIZE.*;
import static jnr.a64asm.SYSREG_CODE.SPSR_EL1;
import static jnr.a64asm.SYSREG_CODE.SYSREG_MAX;

public class SysRegister extends Operand {

    SYSREG_CODE sysRegEnum;
    private static final SysRegister[] sys = new SysRegister[305];

    public SysRegister(SYSREG_CODE sysRegEnum) {
        super(OP.OP_SYSREG, SIZE_DWORD);
       this.sysRegEnum = sysRegEnum;
    }
    static {
        for (SYSREG_CODE i = SPSR_EL1; i.ordinal()< SYSREG_MAX.ordinal();) {
                sys[i.ordinal()] = new SysRegister(i);
                i = SYSREG_CODE.valueOf(i.ordinal() +1);
        }
    }

    public static final SysRegister sysReg(SYSREG_CODE reg) {
        return sys[reg.ordinal()];
    }

    public SYSREG_CODE getEnum(){
        return this.sysRegEnum;
    }

}
