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

public final class PRFOP_ENUM extends Operand{
    private final long value;
    public static final int PLDL1KEEP = 0;
    public static final int PLDL1STRM = 1;
    public static final int PLDL2KEEP = 2;
    public static final int PLDL2STRM = 3;
    public static final int PLDL3KEEP = 4;
    public static final int PLDL3STRM = 5;
    //TBD,
    //TBD1,
    public static final int PLIL1KEEP = 8;
    public static final int PLIL1STRM = 9;
    public static final int PLIL2KEEP = 10;
    public static final int PLIL2STRM = 11;
    public static final int PLIL3KEEP = 12;
    public static final int PLIL3STRM = 13;
    //TBD2,
    //TBD3,
    public static final int PSTL1KEEP = 16;
    public static final int  PSTL1STRM = 17;
    public static final int PSTL2KEEP = 18;
    public static final int  PSTL2STRM = 19;
    public static final int PSTL3KEEP = 20;
    public static final int PSTL3STRM = 21;

    public PRFOP_ENUM( int value) {
        super(OP.OP_PRFOP, 0);
        this.value = value;
    }

    public final long intValue() {
        return this.value;
    }

}
