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

public final class Ext extends Operand {
    private final long value;
    private final long type;

    public Ext(long type, long value) {
        super(OP.OP_EXT, 0);
        this.value = value;
        this.type = type;

    }

    public long value() {
        return value;
    }

    public long type() {
        return type;
    }

}
