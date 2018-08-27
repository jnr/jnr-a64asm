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
