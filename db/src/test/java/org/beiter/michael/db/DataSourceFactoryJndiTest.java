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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DataSourceFactoryJndiTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

    private static final String JNDI_NAME = "java:/comp/env/jdbc/h2db";
    private static final int JNDI_MAX_CONNECTIONS = 2;

    /**
     * Register a connection pool for the H2 test database with JNDI
     *
     * @throws NamingException When the context cannot be registered or the data source cannot be bound to the context
     */
    @BeforeClass
    public static void setUpJndiConnectionPool()
            throws NamingException {

        JndiUtil.setUpJndiConnectionPool(JNDI_MAX_CONNECTIONS, JNDI_NAME);
    }

    /**
     * Start the in-memory database server
     *
     * @throws SQLException When the startup fails
     */
    @BeforeClass
    public static void startDbServer()
            throws SQLException {

        H2Server.start();
    }

    /**
     * Stops the in-memory database server
     */
    @AfterClass
    public static void stopDbServer() {

        H2Server.stop();
    }

    /**
     * Initialize the database with a default database schema + values
     *
     * @throws SQLException When the initialization fails
     */
    @Before
    public void initDatabase()
            throws SQLException {

        H2Server.init();
    }


    /**
     * Test that the JNDI factory method does not accept a null name
     *
     * @throws NullPointerException When a null name is provided
     */
    @Test(expected = NullPointerException.class)
    public void jndiConstructorNullNameTest() {

        String jndiName = null;

        try {
            DataSourceFactory.getDataSource(jndiName);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the JNDI factory method does not accept an empty name
     *
     * @throws IllegalArgumentException When an empty / blank name is provided
     */
    @Test(expected = IllegalArgumentException.class)
    public void jndiConstructorEmptyNameTest() {

        try {
            DataSourceFactory.getDataSource("");
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the JNDI factory throws an exception when an illegal (i.e. not-configured) name is provided
     *
     * @throws FactoryException When a name is provided that has not been configured in JNDI
     */
    @Test(expected = FactoryException.class)
    public void jndiConstructorIllegalNameTest()
            throws FactoryException {

        DataSourceFactory.getDataSource("IllegalName");
    }

    /**
     * Test that the JNDI factory method returns a data source
     */
    @Test
    public void jndiConstructorDataSourceTest() {

        try {
            DataSource ds = DataSourceFactory.getDataSource(JNDI_NAME);

            String error = "The data source is null";
            assertThat(error, ds, notNullValue());

        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the JNDI factory method returns the same
     * data source if called multiple times with the same name
     */
    @Test
    public void jndiConstructorMultipleConnectionTest() {

        try {
            DataSource ds1 = DataSourceFactory.getDataSource(JNDI_NAME);
            DataSource ds2 = DataSourceFactory.getDataSource(JNDI_NAME);

            String error = "The data source 1 is null";
            assertThat(error, ds1, notNullValue());

            error = "The data source 2 is null";
            assertThat(error, ds2, notNullValue());

            error = "The data source 2 is not equal to data source 1";
            assertThat(error, ds2, is(equalTo(ds1)));

        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }
}
