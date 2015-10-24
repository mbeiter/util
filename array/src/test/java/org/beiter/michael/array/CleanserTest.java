/*
 * #%L
 * This file is part of an array utilities library.
 * %%
 * Copyright (C) 2014 - 2015 Michael Beiter <michael@beiter.org>
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

import org.junit.Test;

import java.security.SecureRandom;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class CleanserTest {

    /**
     * Test that the {@link Cleanser#wipe(byte[])} can handle {@code null} references
     */
    @Test
    public void nullByteArrayTest() {

        byte[] empty = null;
        Cleanser.wipe(empty);
    }

    /**
     * Test that the {@link Cleanser#wipe(byte[])} can handle empty source arrays
     */
    @Test
    public void emptyByteArrayTest() {

        byte[] empty = new byte[0];
        Cleanser.wipe(empty);
    }

    /**
     * Zero a byte array and assert that all values have been overwritten
     */
    @Test
    public void zeroByteArrayTest() {

        byte[] random = new byte[16];
        new SecureRandom().nextBytes(random);

        // Check that the array contains at least one byte that is not null. This makes the unit test probabilistic,
        // but the probability of a secure random string with all zeros is extremely low.
        int count = 0;
        for (byte e : random)
            if (e != (byte) 0)
                ++count;

        String error = "All elements in the random array are equal to 0";
        assertThat(error, count, is(greaterThan(0)));

        // now zero the array, and make sure that all bytes are equal to 0
        Cleanser.wipe(random);
        count = 0;
        for (byte e : random)
            if (e != (byte) 0)
                ++count;

        error = "The random array contains at least one element that is unequal to 0";
        assertThat(error, count, is(equalTo(0)));
    }

    /**
     * Test that the {@link Cleanser#wipe(char[])} can handle {@code null} references
     */
    @Test
    public void nullCharArrayTest() {

        char[] empty = null;
        Cleanser.wipe(empty);
    }

    /**
     * Test that the {@link Cleanser#wipe(char[])} can handle empty source arrays
     */
    @Test
    public void emptyCharArrayTest() {

        char[] empty = new char[0];
        Cleanser.wipe(empty);
    }

    /**
     * Zero a char array and assert that all values have been overwritten
     */
    @Test
    public void zeroCharArrayTest() {

        char[] random = UUID.randomUUID().toString().toCharArray();

        // Check that the array does not contain '\0' (works really well for UUIDs...)
        int count = 0;
        for (char e : random)
            if (e == '\0')
                ++count;

        String error = "The random array contains at least one element that is equal to '\0'";
        assertThat(error, count, is(equalTo(0)));

        // now zero the array, and make sure that all chars are equal to '\0'
        Cleanser.wipe(random);
        count = 0;
        for (char e : random)
            if (e != '\0')
                ++count;

        error = "The random array contains at least one element that is unequal to '\0'";
        assertThat(error, count, is(equalTo(0)));
    }
}
