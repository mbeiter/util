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

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * The tests in this class use multi-byte (UTF-8) and single-byte (ISO 8859-1) encodings, with some characters that use
 * more than one bytes in UTF-8 encoding.
 */
public class ConverterTest {

    /**
     * Test that the {@link Converter#toBytes(char[])} and {@link Converter#toBytes(char[], String)} can handle
     * {@code null} references
     */
    @Test
    public void nullCharArrayTest() {

        char[] empty = null;
        Converter.toBytes(empty);

        Converter.toBytes(empty, "UTF-8");
    }

    /**
     * Test that the {@link Converter#toBytes(char[])} and {@link Converter#toBytes(char[], String)} can handle empty
     * source arrays
     */
    @Test
    public void emptyCharArrayTest() {

        char[] empty = new char[0];
        Converter.toBytes(empty);

        Converter.toBytes(empty, "UTF-8");
    }

    /**
     * Convert a char array to a byte array
     */
    @Test
    public void charToByteTest() {

        String sourceString = "This is a test: \u00C4-\u00D6-\u00DC";
        char[] input_asChar = sourceString.toCharArray();
        byte[] input_asUTF8Bytes;
        try {
            input_asUTF8Bytes = sourceString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            AssertionError ae = new AssertionError("Unsupported encoding");
            ae.initCause(e);
            throw ae;
        }
        byte[] input_asISO88591Bytes;
        try {
            input_asISO88591Bytes = sourceString.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            AssertionError ae = new AssertionError("Unsupported encoding");
            ae.initCause(e);
            throw ae;
        }

        // first test that we can convert a multi-byte encoding (UTF-8)
        byte[] result = Converter.toBytes(input_asChar, "UTF-8");
        String error = "The result of the conversion does not meet the expected result (UTF-8 encoding)";
        assertThat(error, result, is(equalTo(input_asUTF8Bytes)));

        // second, test that we can convert a single-byte encoding (ISO 8859-1)
        result = Converter.toBytes(input_asChar, "ISO-8859-1");
        error = "The result of the conversion does not meet the expected result (ISO-8859-1 encoding)";
        assertThat(error, result, is(equalTo(input_asISO88591Bytes)));
    }

    /**
     * Convert a char array to a byte array with an unsupported charset
     */
    @Test(expected = UnsupportedCharsetException.class)
    public void charToByteUnsupportedCharsetTest() {

        String sourceString = "This is a test: \u00C4-\u00D6-\u00DC";
        char[] input_asChar = sourceString.toCharArray();
        byte[] result = Converter.toBytes(input_asChar, "invalid_charset");
    }

    /**
     * Test that the {@link Converter#toChars(byte[])}} and {@link Converter#toChars(byte[], String)}} can handle
     * {@code null} references
     */
    @Test
    public void nullByteArrayTest() {

        byte[] empty = null;
        Converter.toChars(empty);

        Converter.toChars(empty, "UTF-8");
    }

    /**
     * Test that the {@link Converter#toChars(byte[])}} and {@link Converter#toChars(byte[], String)}} can handle
     * empty source arrays
     */
    @Test
    public void emptyByteArrayTest() {

        byte[] empty = new byte[0];
        Converter.toChars(empty);

        Converter.toChars(empty, "UTF-8");
    }

    /**
     * Convert a byte array to a char array
     */
    @Test
    public void byteToCharTest() {

        byte[] sourceBytes_UTF8 = new byte[] {
                0x41, // A
                0x42, // B
                0x43, // C
                0x44, // D
                0x20, // SPACE
                (byte) 0xc3, (byte) 0x84, // \u00C4
                0x2d, // -
                (byte) 0xc3, (byte) 0x96, // \u00D6
                0x2d, // -
                (byte) 0xc3, (byte) 0x9c // \u00DC
        };
        byte[] sourceBytes_ISO88591 = new byte[] {
                0x41, // A
                0x42, // B
                0x43, // C
                0x44, // D
                0x20, // SPACE
                (byte) 0xc4, // \u00C4
                0x2d, // -
                (byte) 0xd6, // \u00D6
                0x2d, // -
                (byte) 0xdc // \u00DC
        };
        char[] expectedChar = "ABCD \u00C4-\u00D6-\u00DC".toCharArray();

        // first test that we can convert a multi-byte encoding (UTF-8)
        char[] result = Converter.toChars(sourceBytes_UTF8, "UTF-8");
        String error = "The result of the conversion does not meet the expected result (UTF-8 encoding)";
        assertThat(error, result, is(equalTo(expectedChar)));

        // second, test that we can convert a single byte encoding (ISO 8859-1)
        result = Converter.toChars(sourceBytes_ISO88591, "ISO-8859-1");
        error = "The result of the conversion does not meet the expected result (ISO-8859-1 encoding)";
        assertThat(error, result, is(equalTo(expectedChar)));
    }

    /**
     * Convert a byte array to a char array with an unsupported charset
     */
    @Test(expected = UnsupportedCharsetException.class)
    public void byteToCharUnsupportedCharsetTest() {

        byte[] sourceBytes = new byte[]{};
        char[] result = Converter.toChars(sourceBytes, "invalid_charset");
    }

    /**
     * ED test: convert char to byte and back to char using UTF-8 encoding.
     */
    @Test
    public void edUTF8Test() {

        String sourceString = "This is a test: \u00C4-\u00D6-\u00DC";
        char[] input_asChar = sourceString.toCharArray();
        byte[] input_asUTF8Bytes;
        try {
            input_asUTF8Bytes = sourceString.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            AssertionError ae = new AssertionError("Unsupported encoding");
            ae.initCause(e);
            throw ae;
        }

        byte[] result_e = Converter.toBytes(input_asChar, "UTF-8");
        String error = "The result of the conversion does not meet the expected result (UTF-8 encoding)";
        assertThat(error, result_e, is(equalTo(input_asUTF8Bytes)));

        char[] result_ed = Converter.toChars(result_e, "UTF-8");
        error = "The result of the conversion does not meet the expected result (UTF-8 encoding)";
        assertThat(error, result_ed, is(equalTo(input_asChar)));

        // If we try to decode a byte array with the wrong encoding (ISO 8859-1 in this case),
        // we should get a non-matching result:
        result_ed = Converter.toChars(result_e, "ISO-8859-1");
        error = "The result of the conversion does not meet the expected result "
                + "(UTF-8 encoding should not equal ISO-8859-1 encoding)";
        assertThat(error, result_ed, is(not(equalTo(input_asChar))));
    }

    /**
     * ED test: convert char to byte and back to char using ISO 8859-1 encoding.
     */
    @Test
    public void edISO88591Test() {

        String sourceString = "This is a test: \u00C4-\u00D6-\u00DC";
        char[] input_asChar = sourceString.toCharArray();
        byte[] input_asISO88591Bytes;
        try {
            input_asISO88591Bytes = sourceString.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            AssertionError ae = new AssertionError("Unsupported encoding");
            ae.initCause(e);
            throw ae;
        }

        byte[] result_e = Converter.toBytes(input_asChar, "ISO-8859-1");
        String error = "The result of the conversion does not meet the expected result (ISO-8859-1 encoding)";
        assertThat(error, result_e, is(equalTo(input_asISO88591Bytes)));

        char[] result_ed = Converter.toChars(result_e, "ISO-8859-1");
        error = "The result of the conversion does not meet the expected result (ISO-8859-1 encoding)";
        assertThat(error, result_ed, is(equalTo(input_asChar)));

        // If we try to decode a byte array with the wrong encoding (UTF-8 in this case),
        // we should get a non-matching result:
        result_ed = Converter.toChars(result_e, "UTF-8");
        error = "The result of the conversion does not meet the expected result "
                + "(UTF-8 encoding should not equal ISO-8859-1 encoding)";
        assertThat(error, result_ed, is(not(equalTo(input_asChar))));
    }
}
