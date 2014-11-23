/*
 * #%L
 * This file is part of a universal JDBC Connection factory.
 * %%
 * Copyright (C) 2014 Michael Beiter <michael@beiter.org>
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
package org.beiter.michael.db;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConnectionSpecTest {

    /**
     * Test that the connection pool spec accepts and correctly returns values
     */
    @Test
    public void setterAndGetterTest() {

        final String url = "testURL";
        final String username = "testUsername";
        final String password = "testPassword";

        ConnectionSpec spec = new ConnectionSpec(url, username, password);

        String error = "maxTotal does not match expected value";
        assertThat(error, spec.getUrl(), is(equalTo(url)));

        error = "maxIdle does not match expected value";
        assertThat(error, spec.getUser(), is(equalTo(username)));

        error = "minIdle does not match expected value";
        assertThat(error, spec.getPassword(), is(equalTo(password)));
    }

    /**
     * Test that a null value for url is not accepted by the constructor
     */
    @Test(expected = NullPointerException.class)
    public void nullUrlTest() {

        final String url = null;
        final String username = "testUsername";
        final String password = "testPassword";

        new ConnectionSpec(url, username, password);
    }

    /**
     * Test that a null value for user is not accepted by the constructor
     */
    @Test(expected = NullPointerException.class)
    public void nullUserTest() {

        final String url = "testURL";
        final String username = null;
        final String password = "testPassword";

        new ConnectionSpec(url, username, password);
    }

    /**
     * Test that a null value for password is not accepted by the constructor
     */
    @Test(expected = NullPointerException.class)
    public void nullPasswordTest() {

        final String url = "testURL";
        final String username = "testUsername";
        final String password = null;

        new ConnectionSpec(url, username, password);
    }
}
