/*
 * #%L
 * This file is part of a universal JDBC Connection factory.
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
package org.beiter.michael.db;

import org.apache.commons.lang3.Validate;

/**
 * This class specifies basic properties for a database connection.
 */
public class ConnectionSpec {

    /**
     * The JDBC URL
     */
    private final String pUrl;

    /**
     * The username for the connection
     */
    private final String pUsername;

    /**
     * The password for the connection
     */
    private final String pPassword;

    /**
     * Constructs a driver spec from the given parameters.
     *
     * @param url      The JDBC database URL of the form <code>jdbc:subprotocol:subname</code>
     * @param username The username for the connection
     * @param password The password for the connection
     */
    public ConnectionSpec(final String url, final String username, final String password) {

        // none of these values must be null, but blank values are allowed for all of them
        Validate.notNull(url);
        Validate.notNull(username);
        Validate.notNull(password);

        // no need for defensive copies of Strings

        this.pUrl = url;
        this.pUsername = username;
        this.pPassword = password;
    }

    /**
     * Returns the URL of the database
     *
     * @return the URL
     */
    public final String getUrl() {

        // no need for defensive copies of Strings

        return pUrl;
    }

    /**
     * Returns the username for the connection
     *
     * @return the username
     */
    public final String getUser() {

        // no need for defensive copies of Strings

        return pUsername;
    }

    /**
     * Returns the password for the connection
     *
     * @return the password
     */
    public final String getPassword() {

        // no need for defensive copies of Strings

        return pPassword;
    }
}
