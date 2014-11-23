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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConnectionFactoryDirectTest {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);

    private static final String DRIVER = H2Server.DRIVER;
    private static final String URL = H2Server.URL;
    private static final String USER = H2Server.USER;
    private static final String PASSWORD = H2Server.PASSWORD;
    private static final int POOL_MAX_CONNECTIONS = 2;

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
        ConnectionFactory.reset();
    }


    /**
     * Test that the direct factory method does not accept a null driver
     *
     * @throws NullPointerException When a null driver is provided
     */
    @Test(expected = NullPointerException.class)
    public void directConstructorNullDriverTest() {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();

        try {
            ConnectionFactory.getConnection(null, connSpec, poolSpec);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not accept a null connection spec
     *
     * @throws NullPointerException When a null connection spec is provided
     */
    @Test(expected = NullPointerException.class)
    public void directConstructorNullConnSpecTest() {

        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();

        try {
            ConnectionFactory.getConnection(DRIVER, null, poolSpec);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not accept a null pool spec
     *
     * @throws NullPointerException When a null connection pool spec is provided
     */
    @Test(expected = NullPointerException.class)
    public void directConstructorNullPoolSpecTest() {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);

        try {
            ConnectionFactory.getConnection(DRIVER, connSpec, null);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not accept an empty driver
     *
     * @throws IllegalArgumentException When an empty / blank driver is provided
     */
    @Test(expected = IllegalArgumentException.class)
    public void directConstructorEmptyDriverTest() {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();

        try {
            ConnectionFactory.getConnection("", connSpec, poolSpec);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory throws an exception when an illegal (i.e. non-existing) driver is provided
     *
     * @throws FactoryException When a driver is provided that does not exist in the class path
     */
    @Test(expected = FactoryException.class)
    public void directConstructorIllegalDriverTest()
            throws FactoryException {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();

        ConnectionFactory.getConnection("IllegalDriver", connSpec, poolSpec);
    }

    /**
     * Test that the direct factory method returns a connection
     */
    @Test
    public void directConstructorConnectionTest() {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();

        try {
            Connection con = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);

            String error = "The DB connection is null";
            assertThat(error, con, notNullValue());

            con.close();
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        } catch (SQLException e) {
            AssertionError ae = new AssertionError("Error closing the connection");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method returns two different connections if called multiple times
     */
    @Test
    public void directConstructorMultipleConnectionTest() {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();
        poolSpec.setMaxTotal(POOL_MAX_CONNECTIONS);
        poolSpec.setMaxWaitMillis(0); // fail with an exception if no connections are available in the pool

        try {
            Connection con1 = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);
            Connection con2 = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);

            String error = "The DB connection 1 is null";
            assertThat(error, con1, notNullValue());

            error = "The DB connection 2 is null";
            assertThat(error, con2, notNullValue());

            error = "The DB connection 2 is equal to DB connection 1";
            assertThat(error, con2, is(not(equalTo(con1))));

            con1.close();
            con2.close();

        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        } catch (SQLException e) {
            AssertionError ae = new AssertionError("Error closing the connection");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not return more connections than are available in the pool
     * <p/>
     * Note that this test is prone to resource leaks und certain conditions, which resuly in the borrowed connections
     * not being properly returned to the pool. This is still okay for the unit tests, because the in-memory DB server
     * used for the tests is destroyed after the tests are complete.
     *
     * @throws FactoryException When the instantiation of the connections does not work (expected)
     * @throws SQLException                  When the connections cannot be closed
     */
    @Test(expected = FactoryException.class)
    public void directConstructorMultipleConnectionExhaustedPoolTest()
            throws FactoryException, SQLException {

        ConnectionSpec connSpec = new ConnectionSpec(URL, USER, PASSWORD);
        ConnectionPoolSpec poolSpec = new ConnectionPoolSpec();
        poolSpec.setMaxTotal(POOL_MAX_CONNECTIONS);
        poolSpec.setMaxWaitMillis(0); // fail with an exception if no connections are available in the pool

        Connection con1 = null;
        Connection con2 = null;
        Connection con3 = null;
        try {
            con1 = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);
            con2 = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);

            String error = "The DB connection 1 is null";
            assertThat(error, con1, notNullValue());

            error = "The DB connection 2 is null";
            assertThat(error, con2, notNullValue());

            error = "The DB connection 2 is equal to DB connection 1";
            assertThat(error, con2, is(not(equalTo(con1))));

            // the pool supports only 2 connections (see JNDI_MAX_CONNECTIONS)
            // borrowing a third connection will result in a FactoryException because the pool is exhausted
            con3 = ConnectionFactory.getConnection(DRIVER, connSpec, poolSpec);
        } finally {

            if (con1 != null) {
                LOG.debug("closing connection 'con1'");
                con1.close();
                LOG.debug("'con1' has been closed");
            }

            if (con2 != null) {
                LOG.debug("closing connection 'con2'");
                con2.close();
                LOG.debug("'con2' has been closed");
            }

            if (con3 != null) {
                LOG.debug("closing connection 'con3'");
                con3.close();
                LOG.debug("'con3' has been closed");
            }
        }
    }
}
