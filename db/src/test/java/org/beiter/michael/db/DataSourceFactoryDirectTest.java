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

import org.beiter.michael.db.propsbuilder.MapBasedConnPropsBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DataSourceFactoryDirectTest {

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

        // Using the properties builder instead of
        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver(null);
        connProps.setUrl(URL);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        try {
            DataSourceFactory.getDataSource(connProps);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not accept a null url
     *
     * @throws NullPointerException When a null connection spec is provided
     */
    @Test(expected = NullPointerException.class)
    public void directConstructorNullConnSpecTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver(DRIVER);
        connProps.setUrl(null);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        try {
            DataSourceFactory.getDataSource(connProps);
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
    public void directConstructorNullconnPropsTest() {

        ConnectionProperties connProps = null;
        
        try {
            DataSourceFactory.getDataSource(connProps);
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

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver("");
        connProps.setUrl(URL);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        try {
            DataSourceFactory.getDataSource(connProps);
        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method does not accept an empty url
     *
     * @throws IllegalArgumentException When an empty / blank url is provided
     */
    @Test(expected = IllegalArgumentException.class)
    public void directConstructorEmptyUrlTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver(DRIVER);
        connProps.setUrl("");
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        try {
            DataSourceFactory.getDataSource(connProps);
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

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver("IllegalDriver");
        connProps.setUrl(URL);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        DataSourceFactory.getDataSource(connProps);
    }

    /**
     * Test that the direct factory method returns a data source
     */
    @Test
    public void directConstructorConnectionTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver(DRIVER);
        connProps.setUrl(URL);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);

        try {
            DataSource ds = DataSourceFactory.getDataSource(connProps);

            String error = "The data source is null";
            assertThat(error, ds, notNullValue());

        } catch (FactoryException e) {
            AssertionError ae = new AssertionError("Factory error");
            ae.initCause(e);
            throw ae;
        }
    }

    /**
     * Test that the direct factory method returns the same data source
     * if called multiple times with the same pool properties
     */
    @Test
    public void directConstructorMultipleConnectionTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();
        connProps.setDriver(DRIVER);
        connProps.setUrl(URL);
        connProps.setUsername(USER);
        connProps.setPassword(PASSWORD);
        
        connProps.setMaxTotal(POOL_MAX_CONNECTIONS);
        connProps.setMaxWaitMillis(0); // fail with an exception if no connections are available in the pool

        try {
            DataSource ds1 = DataSourceFactory.getDataSource(connProps);
            DataSource ds2 = DataSourceFactory.getDataSource(connProps);

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
