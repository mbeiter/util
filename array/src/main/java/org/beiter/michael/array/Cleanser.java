/*
 * #%L
 * This file is part of an array utilities library.
 * %%
 * Copyright (C) 2014 - 2016 Michael Beiter <michael@beiter.org>
 * %%
 * All rights reserved.
 * .
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the copyright holder nor the names of the
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 * .
 * .
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.beiter.michael.array;

import java.util.Arrays;

/**
 * A utility class with methods to clear arrays.
 */
@SuppressWarnings("PMD.ShortClassName")
public final class Cleanser {

    /**
     * A private constructor to prevent instantiation of this class
     */
    private Cleanser() {
    }

    /**
     * Wipe the contents of the provided array from memory.
     *
     * @param src the array to clear
     */
    // Cannot use varargs here, this would be against the point!
    @SuppressWarnings("PMD.UseVarargs")
    public static void wipe(final byte[] src) {

        if (src != null) {
            Arrays.fill(src, (byte) 0);
        }
    }

    /**
     * Wipe the contents of the provided array from memory.
     *
     * @param src the array to clear
     */
    // Cannot use varargs here, this would be against the point!
    @SuppressWarnings("PMD.UseVarargs")
    public static void wipe(final char[] src) {

        if (src != null) {
            Arrays.fill(src, '\0');
        }
    }
}
