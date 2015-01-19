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

import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConnectionFactoryJndiTest {

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

        LOG.info("Registering JNDI name");

        // create a data source from the H2 default connection pool of the test database
        JdbcConnectionPool pool = JdbcConnectionPool.create(H2Server.URL, H2Server.USER, H2Server.PASSWORD);
        pool.setMaxConnections(JNDI_MAX_CONNECTIONS);
        pool.setLoginTimeout(1); // set a short login timeout in case of errors

        // this requires the tomcat dependency to work, but is required to init and bind the context
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");

        // init and bind
        Context context = new InitialContext();
        context.createSubcontext("java:");
        context.createSubcontext("java:/comp");
        context.createSubcontext("java:/comp/env");
        context.createSubcontext("java:/comp/env/jdbc");
        context.bind(JNDI_NAME, pool);
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

        try {
            ConnectionFactory.getConnection(null);
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
            ConnectionFactory.getConnection("");
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

        ConnectionFactory.getConnection("IllegalName");
    }

    /**
     * Test that the JNDI factory method returns a connection
     */
    @Test
    public void jndiConstructorConnectionTest() {

        try {
            Connection con = ConnectionFactory.getConnection(JNDI_NAME);

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
     * Test that the JNDI factory method returns two different connections if called multiple times
     */
    @Test
    public void jndiConstructorMultipleConnectionTest() {

        try {
            Connection con1 = ConnectionFactory.getConnection(JNDI_NAME);
            Connection con2 = ConnectionFactory.getConnection(JNDI_NAME);

            String error = "The DB connection 1 is null";
            assertThat(error, con1, notNullValue());

            error = "The DB connection 2 is null";
            assertThat(error, con2, notNullValue());

            error = "The DB connection 2 is equal to DB connection 1";
            assertThat(error, con2, is(not(equalTo(con1))));

            // the pool supports only 2 connections (see JNDI_MAX_CONNECTIONS)
            // get access to the pool, and check that there are two active connections
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup(JNDI_NAME);
            context.close();
            JdbcConnectionPool pool = (JdbcConnectionPool) dataSource;

            error = "Number of active connections in pool (" + pool.getActiveConnections() + ") does not match expectations (2)";
            assertThat(error, pool.getActiveConnections(), is(equalTo(2)));

            // close one connection and check again
            con2.close();

            error = "Number of active connections in pool (" + pool.getActiveConnections() + ") does not match expectations (1)";
            assertThat(error, pool.getActiveConnections(), is(equalTo(1)));

            // close the last connection
            con1.close();
            error = "Number of active connections in pool (" + pool.getActiveConnections() + ") does not match expectations (0)";
            assertThat(error, pool.getActiveConnections(), is(equalTo(0)));
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        } catch (NamingException e) {
            AssertionError ae = new AssertionError("Context lookup error");
            ae.initCause(e);
            throw ae;
        } catch (SQLException e) {
            AssertionError ae = new AssertionError("Error closing the connection");
            ae.initCause(e);
            throw ae;
        }
    }
}
