/*
 * #%L
 * This file is part of a universal JDBC Connection factory.
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
package org.beiter.michael.db;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class creates and manages JDBC Connection instances either from:
 * <ul>
 * <li>a named JNDI managed connection or</li>
 * <li>a connection pool that is maintained by the {@link DataSourceFactory} factory</li>
 * </ul>
 */
public final class ConnectionFactory {

    /**
     * The logger object for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

    /**
     * A private constructor to prevent instantiation of this class
     */
    private ConnectionFactory() {
    }

    /**
     * Return a Connection instance for a JNDI managed JDBC connection.
     *
     * @param jndiName The JNDI connection name
     * @return a JDBC connection
     * @throws FactoryException         When the connection cannot be retrieved from JNDI
     * @throws NullPointerException     When {@code jndiName} is null
     * @throws IllegalArgumentException When {@code jndiName} is empty
     */
    public static Connection getConnection(final String jndiName)
            throws FactoryException {

        Validate.notBlank(jndiName, "The validated character sequence 'jndiName' is null or empty");

        // no need for defensive copies of Strings

        try {
            return DataSourceFactory.getDataSource(jndiName).getConnection();
        } catch (SQLException e) {
            final String error = "Error retrieving JDBC connection from JNDI: " + jndiName;
            LOG.warn(error);
            throw new FactoryException(error, e);
        }
    }

    /**
     * Return a Connection instance from a pool that manages JDBC driver based connections.
     * <p>
     * The driver-based connection are managed in a connection pool. The pool is created using the provided properties
     * for both the connection and the pool spec. Once the pool has been created, it is cached (based on URL and
     * username), and can no longer be changed. Subsequent calls to this method will return a connection from the
     * cached pool, and changes in the pool spec (e.g. changes to the size of the pool) will be ignored.
     *
     * @param poolSpec A connection pool spec that has the driver and url configured as non-empty strings
     * @return a JDBC connection
     * @throws FactoryException         When the connection cannot be retrieved from the pool, or the pool cannot be
     *                                  created
     * @throws NullPointerException     When the {@code poolSpec}, {@code poolSpec.getDriver()}, or
     *                                  {@code poolSpec.getUrl()} are {@code null}
     * @throws IllegalArgumentException When {@code poolSpec.getDriver()} or {@code poolSpec.getUrl()} are empty
     */
    public static Connection getConnection(final ConnectionProperties poolSpec)
            throws FactoryException {

        Validate.notNull(poolSpec, "The validated object 'poolSpec' is null");
        Validate.notBlank(poolSpec.getDriver(),
                "The validated character sequence 'poolSpec.getDriver()' is null or empty");
        Validate.notBlank(poolSpec.getUrl(), "The validated character sequence 'poolSpec.getUrl()' is null or empty");

        // no need for defensive copies of Strings

        try {
            return DataSourceFactory.getDataSource(poolSpec).getConnection();
        } catch (SQLException e) {
            // a connection is identified by the URL, the username, and the password
            final String key = String.format("%s:%s", poolSpec.getUrl(), poolSpec.getUsername());
            final String error = "Error retrieving JDBC connection from pool: " + key;
            LOG.warn(error);
            throw new FactoryException(error, e);
        }
    }

    /**
     * Resets the internal state of the {@link DataSourceFactory} that manages the data source pools exposed by this
     * factory.
     * <p>
     * <strong>This method does not release any resources that have been borrowed from the connection pools managed
     * by this factory.</strong> To avoid resource leaks, you <strong>must</strong> close / return all connections to
     * their pools before calling this method.
     */
    public static void reset() {

        // Unset the cached connections
        DataSourceFactory.reset();
    }
}
