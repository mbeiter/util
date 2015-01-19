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

import org.junit.Test;

import java.sql.Connection;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ConnectionPoolSpecTest {

    /**
     * maxTotal test
     */
    @Test
    public void maxTotalTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "maxTotal does not match expected default value";
        assertThat(error, spec.getMaxTotal(), is(equalTo(8)));
        error = "maxTotal does not match expected value";
        spec.setMaxTotal(42);
        assertThat(error, spec.getMaxTotal(), is(equalTo(42)));
    }

    /**
     * maxIdle test
     */
    @Test
    public void maxIdleTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "maxIdle does not match expected default value";
        assertThat(error, spec.getMaxIdle(), is(equalTo(8)));
        error = "maxIdle does not match expected value";
        spec.setMaxIdle(42);
        assertThat(error, spec.getMaxIdle(), is(equalTo(42)));
    }

    /**
     * minIdle test
     */
    @Test
    public void minIdleTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "minIdle does not match expected default value";
        assertThat(error, spec.getMinIdle(), is(equalTo(0)));
        error = "minIdle does not match expected value";
        spec.setMinIdle(42);
        assertThat(error, spec.getMinIdle(), is(equalTo(42)));
    }

    /**
     * illegal minIdle test
     */
    @Test(expected = IllegalArgumentException.class)
    public void minIdleIllegalTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        spec.setMinIdle(-1);
    }

    /**
     * maxWaitMillis test
     */
    @Test
    public void maxWaitMillisTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "maxWaitMillis does not match expected default value";
        assertThat(error, spec.getMaxWaitMillis(), is(equalTo(-1l)));
        error = "maxWaitMillis does not match expected value";
        spec.setMaxWaitMillis(42);
        assertThat(error, spec.getMaxWaitMillis(), is(equalTo(42l)));
    }

    /**
     * illegal maxWaitMillis test
     */
    @Test(expected = IllegalArgumentException.class)
    public void maxWaitMillisIllegalTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        spec.setMaxWaitMillis(-2);
    }

    /**
     * testOnCreate test
     */
    @Test
    public void testOnCreateTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "testOnCreate does not match expected default value";
        assertThat(error, spec.isTestOnCreate(), is(equalTo(false)));
        error = "testOnCreate does not match expected value";
        spec.setTestOnCreate(true);
        assertThat(error, spec.isTestOnCreate(), is(equalTo(true)));
    }

    /**
     * testOnBorrow test
     */
    @Test
    public void testOnBorrowTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "testOnBorrow does not match expected default value";
        assertThat(error, spec.isTestOnBorrow(), is(equalTo(true)));
        error = "testOnBorrow does not match expected value";
        spec.setTestOnBorrow(false);
        assertThat(error, spec.isTestOnBorrow(), is(equalTo(false)));
    }

    /**
     * testOnReturn test
     */
    @Test
    public void testOnReturnTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "testOnReturn does not match expected default value";
        assertThat(error, spec.isTestOnReturn(), is(equalTo(false)));
        error = "testOnReturn does not match expected value";
        spec.setTestOnReturn(true);
        assertThat(error, spec.isTestOnReturn(), is(equalTo(true)));
    }

    /**
     * testOnReturn test
     */
    @Test
    public void testWhileIdleTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "testWhileIdle does not match expected default value";
        assertThat(error, spec.isTestWhileIdle(), is(equalTo(false)));
        error = "testWhileIdle does not match expected value";
        spec.setTestWhileIdle(true);
        assertThat(error, spec.isTestWhileIdle(), is(equalTo(true)));
    }

    /**
     * timeBetweenEvictionRunsMillis test
     */
    @Test
    public void timeBetweenEvictionRunsMillisTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "timeBetweenEvictionRunsMillis does not match expected default value";
        assertThat(error, spec.getTimeBetweenEvictionRunsMillis(), is(equalTo(-1l)));
        error = "timeBetweenEvictionRunsMillis does not match expected value";
        spec.setTimeBetweenEvictionRunsMillis(42);
        assertThat(error, spec.getTimeBetweenEvictionRunsMillis(), is(equalTo(42l)));
    }

    /**
     * illegal numTestsPerEvictionRun test
     */
    @Test(expected = IllegalArgumentException.class)
    public void numTestsPerEvictionRunIllegalTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        spec.setNumTestsPerEvictionRun(0);
    }

    /**
     * numTestsPerEvictionRun test
     */
    @Test
    public void numTestsPerEvictionRunTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "numTestsPerEvictionRun does not match expected default value";
        assertThat(error, spec.getNumTestsPerEvictionRun(), is(equalTo(3)));
        error = "numTestsPerEvictionRun does not match expected value";
        spec.setNumTestsPerEvictionRun(42);
        assertThat(error, spec.getNumTestsPerEvictionRun(), is(equalTo(42)));
    }

    /**
     * minEvictableIdleTimeMillis test
     */
    @Test
    public void minEvictableIdleTimeMillisTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "minEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, spec.getMinEvictableIdleTimeMillis(), is(equalTo(1000 * 60 * 30l)));
        error = "minEvictableIdleTimeMillis does not match expected value";
        spec.setMinEvictableIdleTimeMillis(42);
        assertThat(error, spec.getMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * softMinEvictableIdleTimeMillis test
     */
    @Test
    public void softMinEvictableIdleTimeMillisTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "softMinEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, spec.getSoftMinEvictableIdleTimeMillis(), is(equalTo(-1l)));
        error = "softMinEvictableIdleTimeMillis does not match expected value";
        spec.setSoftMinEvictableIdleTimeMillis(42);
        assertThat(error, spec.getSoftMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * lifo test
     */
    @Test
    public void lifoTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "lifo does not match expected default value";
        assertThat(error, spec.isLifo(), is(equalTo(true)));
        error = "lifo does not match expected value";
        spec.setLifo(false);
        assertThat(error, spec.isLifo(), is(equalTo(false)));
    }

    /**
     * defaultAutoCommit test
     */
    @Test
    public void defaultAutoCommitTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "defaultAutoCommit does not match expected default value";
        assertThat(error, spec.isDefaultAutoCommit(), is(equalTo(true)));
        error = "defaultAutoCommit does not match expected value";
        spec.setDefaultAutoCommit(false);
        assertThat(error, spec.isDefaultAutoCommit(), is(equalTo(false)));
    }

    /**
     * defaultReadOnly test
     */
    @Test
    public void defaultReadOnlyTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "defaultReadOnly does not match expected default value";
        assertThat(error, spec.isDefaultReadOnly(), is(equalTo(false)));
        error = "defaultReadOnly does not match expected value";
        spec.setDefaultReadOnly(true);
        assertThat(error, spec.isDefaultReadOnly(), is(equalTo(true)));
    }

    /**
     * defaultTransactionIsolation test
     */
    @Test
    public void defaultTransactionIsolationTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "defaultTransactionIsolation does not match expected default value";
        assertThat(error, spec.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_REPEATABLE_READ)));
        error = "defaultTransactionIsolation does not match expected value";
        spec.setDefaultTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        assertThat(error, spec.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_SERIALIZABLE)));
    }

    /**
     * cacheState test
     */
    @Test
    public void cacheStateTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "cacheState does not match expected default value";
        assertThat(error, spec.isCacheState(), is(equalTo(true)));
        error = "cacheState does not match expected value";
        spec.setCacheState(false);
        assertThat(error, spec.isCacheState(), is(equalTo(false)));
    }

    /**
     * validationQuery test
     */
    @Test
    public void validationQueryTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "validationQuery does not match expected default value";
        assertThat(error, spec.getValidationQuery(), is(equalTo("SELECT 1")));
        error = "validationQuery does not match expected value";
        spec.setValidationQuery("42");
        assertThat(error, spec.getValidationQuery(), is(equalTo("42")));
    }

    /**
     * maxConnLifetimeMillis test
     */
    @Test
    public void maxConnLifetimeMillisTest() {

        ConnectionPoolSpec spec = new ConnectionPoolSpec();

        String error = "maxConnLifetimeMillis does not match expected default value";
        assertThat(error, spec.getMaxConnLifetimeMillis(), is(equalTo(-1l)));
        error = "maxConnLifetimeMillis does not match expected value";
        spec.setMaxConnLifetimeMillis(42);
        assertThat(error, spec.getMaxConnLifetimeMillis(), is(equalTo(42l)));
    }
}
