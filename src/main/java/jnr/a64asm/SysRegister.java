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
