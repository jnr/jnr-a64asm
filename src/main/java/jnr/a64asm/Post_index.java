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
//import static jnr.a64asm.REG.*;

public final class Post_index extends Operand {
    private final Immediate postIndex;
    private final Register basereg;

    public  Post_index(Register base, Immediate postIndex) {
        super(OP.OP_POSTINDEX, 0);
        this.basereg = base;
        this.postIndex = postIndex;
    }

    public final Immediate getPostIndex() {
        return postIndex;
    }

    public final Register getRegister() {
        return basereg;
    }

}
