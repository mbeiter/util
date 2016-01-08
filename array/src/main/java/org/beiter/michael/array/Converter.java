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

import org.apache.commons.lang3.Validate;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * A utility class with methods to convert arrays (e.g. from char[] to byte[]).
 */
@SuppressWarnings("PMD.ShortClassName")
public final class Converter {

    /**
     * A private constructor to prevent instantiation of this class
     */
    private Converter() {
    }

    /**
     * Convert a char array to a byte array using the platform's default encoding as returned by
     * {@link Charset#defaultCharset()}.
     * <p>
     * When writing portable code, it is commonly a better practice to use {@link Converter#toBytes(char[], String)}
     * instead of this method, and specify the encoding explicitly to avoid porting problems.
     * <p>
     * Note that this method does not change the provided source array.
     *
     * @param chars The char array to convert
     * @return The byte[] representation of the provided char array
     */
    // Cannot use varargs here, this would be against the point!
    @SuppressWarnings("PMD.UseVarargs")
    public static byte[] toBytes(final char[] chars) {

        // make sure we can handle null inputs
        if (chars == null) {
            return toBytes(new char[0], Charset.defaultCharset().name());
        } else {
            return toBytes(chars, Charset.defaultCharset().name());
        }
    }

    /**
     * Convert a char array to a byte array using the provided String encoding.
     * <p>
     * Note that this method does not change the provided source array.
     *
     * @param chars    The char array to convert
     * @param encoding The string encoding to use
     * @return The byte[] representation of the provided char array
     * @throws NullPointerException                         When {@code encoding} is null
     * @throws IllegalArgumentException                     When {@code encoding} is empty
     * @throws java.nio.charset.UnsupportedCharsetException When {@code encoding} is invalid
     */
    public static byte[] toBytes(final char[] chars, final String encoding) {

        Validate.notBlank(encoding, "The validated character sequence 'encoding' is null or empty");

        // make sure we can handle null inputs, and create a defensive copy of the input if needed
        char[] myChars;
        if (chars == null) {
            myChars = new char[0];
        } else {
            myChars = Arrays.copyOf(chars, chars.length);
        }

        // conversion
        final Charset charset = Charset.forName(encoding);
        final CharBuffer charBuffer = CharBuffer.wrap(myChars);
        final ByteBuffer byteBuffer = charset.encode(charBuffer);
        final byte[] result = Arrays.copyOfRange(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit());

        // clear confidential data from the array backing the byte buffer
        // there is no need to clear the array in the char buffer, because we simply wrapped the original array, and
        // did not copy it. The caller has to clear that array if they want to wipe any confidential data in the array.
        Cleanser.wipe(byteBuffer.array());

        return result;
    }

    /**
     * Convert a byte array to a char array using the platform's default encoding as returned by
     * {@link Charset#defaultCharset()}.
     * <p>
     * When writing portable code, it is commonly a better practice to use {@link Converter#toBytes(char[], String)}
     * instead of this method, and specify the encoding explicitly to avoid porting problems.
     * <p>
     * Note that this method does not change the provided source array.
     *
     * @param bytes The byte array to convert
     * @return The char[] representation of the provided byte array
     */
    // Cannot use varargs here, this would be against the point!
    @SuppressWarnings("PMD.UseVarargs")
    public static char[] toChars(final byte[] bytes) {

        // make sure we can handle null inputs
        if (bytes == null) {
            return toChars(new byte[0], Charset.defaultCharset().name());
        } else {
            return toChars(bytes, Charset.defaultCharset().name());
        }
    }

    /**
     * Convert a byte array to a char array using the provided String encoding.
     * <p>
     * Note that this method does not change the provided source array.
     *
     * @param bytes    The byte array to convert
     * @param encoding The string encoding to use
     * @return The char[] representation of the provided byte array
     * @throws NullPointerException                         When {@code encoding} is null
     * @throws IllegalArgumentException                     When {@code encoding} is empty
     * @throws java.nio.charset.UnsupportedCharsetException When {@code encoding} is invalid
     */
    public static char[] toChars(final byte[] bytes, final String encoding) {

        Validate.notBlank(encoding, "The validated character sequence 'encoding' is null or empty");

        // make sure we can handle null inputs, and create a defensive copy of the input if needed
        byte[] myBytes;
        if (bytes == null) {
            myBytes = new byte[0];
        } else {
            myBytes = Arrays.copyOf(bytes, bytes.length);
        }

        // conversion
        final Charset charset = Charset.forName(encoding);
        final CharBuffer charBuffer = charset.decode(ByteBuffer.wrap(myBytes));
        final char[] result = Arrays.copyOfRange(charBuffer.array(), charBuffer.position(), charBuffer.limit());

        // clear confidential data from the array backing the char buffer
        // there is no need to clear the array in the byte buffer, because we simply wrapped the original array, and
        // did not copy it. The caller has to clear that array if they want to wipe any confidential data in the array.
        Cleanser.wipe(charBuffer.array());

        return result;
    }
}
