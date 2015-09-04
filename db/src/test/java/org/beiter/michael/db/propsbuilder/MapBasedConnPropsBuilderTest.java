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
package org.beiter.michael.db.propsbuilder;

import org.beiter.michael.db.ConnectionProperties;
import org.junit.Test;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MapBasedConnPropsBuilderTest {

    /**
     * default driver test
     */
    @Test
    public void defaultDriverTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "driver does not match expected default value";
        assertThat(error, connProps.getDriver(), is(nullValue()));
        error = "driver does not match expected value";
        connProps.setDriver("42");
        assertThat(error, connProps.getDriver(), is(equalTo("42")));
    }

    /**
     * driver test
     */
    @Test
    public void driverTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_DRIVER, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "driver does not match expected default value";
        assertThat(error, connProps.getDriver(), is(nullValue()));

        map.put(MapBasedConnPropsBuilder.KEY_DRIVER, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "driver does not match expected value";
        assertThat(error, connProps.getDriver(), is(equalTo("42")));
    }

    /**
     * default url test
     */
    @Test
    public void defaultUrlTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "url does not match expected default value";
        assertThat(error, connProps.getUrl(), is(nullValue()));
        error = "url does not match expected value";
        connProps.setUrl("42");
        assertThat(error, connProps.getUrl(), is(equalTo("42")));
    }

    /**
     * url test
     */
    @Test
    public void urlTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_URL, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "url does not match expected default value";
        assertThat(error, connProps.getUrl(), is(nullValue()));

        map.put(MapBasedConnPropsBuilder.KEY_URL, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "url does not match expected value";
        assertThat(error, connProps.getUrl(), is(equalTo("42")));
    }

    /**
     * default username test
     */
    @Test
    public void defaultUsernameTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "username does not match expected default value";
        assertThat(error, connProps.getUsername(), is(nullValue()));
        error = "username does not match expected value";
        connProps.setUsername("42");
        assertThat(error, connProps.getUsername(), is(equalTo("42")));
    }

    /**
     * username test
     */
    @Test
    public void usernameTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_USERNAME, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "username does not match expected default value";
        assertThat(error, connProps.getUsername(), is(nullValue()));

        map.put(MapBasedConnPropsBuilder.KEY_USERNAME, "");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "username does not match expected value";
        assertThat(error, connProps.getUsername(), isEmptyString());

        map.put(MapBasedConnPropsBuilder.KEY_USERNAME, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "username does not match expected value";
        assertThat(error, connProps.getUsername(), is(equalTo("42")));
    }

    /**
     * default password test
     */
    @Test
    public void defaultPasswordTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "password does not match expected default value";
        assertThat(error, connProps.getPassword(), is(nullValue()));
        error = "password does not match expected value";
        connProps.setPassword("42");
        assertThat(error, connProps.getPassword(), is(equalTo("42")));
    }

    /**
     * password test
     */
    @Test
    public void passwordTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_PASSWORD, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "password does not match expected default value";
        assertThat(error, connProps.getPassword(), is(nullValue()));

        map.put(MapBasedConnPropsBuilder.KEY_PASSWORD, "");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "password does not match expected value";
        assertThat(error, connProps.getPassword(), isEmptyString());

        map.put(MapBasedConnPropsBuilder.KEY_PASSWORD, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "password does not match expected value";
        assertThat(error, connProps.getPassword(), is(equalTo("42")));
    }

    /**
     * default maxTotal test
     */
    @Test
    public void defaultMaxTotalTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "maxTotal does not match expected default value";
        assertThat(error, connProps.getMaxTotal(), is(equalTo(8)));
        error = "maxTotal does not match expected value";
        connProps.setMaxTotal(42);
        assertThat(error, connProps.getMaxTotal(), is(equalTo(42)));
    }

    /**
     * maxTotal test
     */
    @Test
    public void maxTotalTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MAX_TOTAL, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "maxTotal does not match expected default value";
        assertThat(error, connProps.getMaxTotal(), is(equalTo(8)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_TOTAL, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxTotal does not match expected value";
        assertThat(error, connProps.getMaxTotal(), is(equalTo(8)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_TOTAL, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxTotal does not match expected value";
        assertThat(error, connProps.getMaxTotal(), is(equalTo(42)));
    }

    /**
     * default maxIdle test
     */
    @Test
    public void defaultMaxIdleTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "maxIdle does not match expected default value";
        assertThat(error, connProps.getMaxIdle(), is(equalTo(8)));
        error = "maxIdle does not match expected value";
        connProps.setMaxIdle(42);
        assertThat(error, connProps.getMaxIdle(), is(equalTo(42)));
    }

    /**
     * maxIdle test
     */
    @Test
    public void maxIdleTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MAX_IDLE, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "maxIdle does not match expected default value";
        assertThat(error, connProps.getMaxIdle(), is(equalTo(8)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_IDLE, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxIdle does not match expected value";
        assertThat(error, connProps.getMaxIdle(), is(equalTo(8)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_IDLE, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxIdle does not match expected value";
        assertThat(error, connProps.getMaxIdle(), is(equalTo(42)));
    }

    /**
     * default minIdle test
     */
    @Test
    public void defaultMinIdleTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "minIdle does not match expected default value";
        assertThat(error, connProps.getMinIdle(), is(equalTo(0)));
        error = "minIdle does not match expected value";
        connProps.setMinIdle(42);
        assertThat(error, connProps.getMinIdle(), is(equalTo(42)));
    }

    /**
     * minIdle test
     */
    @Test
    public void minIdleTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MIN_IDLE, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "minIdle does not match expected default value";
        assertThat(error, connProps.getMinIdle(), is(equalTo(0)));

        map.put(MapBasedConnPropsBuilder.KEY_MIN_IDLE, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "minIdle does not match expected value";
        assertThat(error, connProps.getMinIdle(), is(equalTo(0)));

        map.put(MapBasedConnPropsBuilder.KEY_MIN_IDLE, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "minIdle does not match expected value";
        assertThat(error, connProps.getMinIdle(), is(equalTo(42)));
    }

    /**
     * illegal minIdle test
     */
    @Test(expected = IllegalArgumentException.class)
    public void minIdleIllegalTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        connProps.setMinIdle(-1);
    }

    /**
     * default maxWaitMillis test
     */
    @Test
    public void defaultMaxWaitMillisTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "maxWaitMillis does not match expected default value";
        assertThat(error, connProps.getMaxWaitMillis(), is(equalTo(-1l)));
        error = "maxWaitMillis does not match expected value";
        connProps.setMaxWaitMillis(42);
        assertThat(error, connProps.getMaxWaitMillis(), is(equalTo(42l)));
    }

    /**
     * maxWaitMillis test
     */
    @Test
    public void maxWaitMillisTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MAX_WAIT_MILLIS, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "maxWaitMillis does not match expected default value";
        assertThat(error, connProps.getMaxWaitMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_WAIT_MILLIS, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxWaitMillis does not match expected value";
        assertThat(error, connProps.getMaxWaitMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_WAIT_MILLIS, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxWaitMillis does not match expected value";
        assertThat(error, connProps.getMaxWaitMillis(), is(equalTo(42l)));
    }

    /**
     * illegal maxWaitMillis test
     */
    @Test(expected = IllegalArgumentException.class)
    public void maxWaitMillisIllegalTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        connProps.setMaxWaitMillis(-2);
    }

    /**
     * default testOnCreate test
     */
    @Test
    public void defaultTestOnCreateTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "testOnCreate does not match expected default value";
        assertThat(error, connProps.isTestOnCreate(), is(equalTo(false)));
        error = "testOnCreate does not match expected value";
        connProps.setTestOnCreate(true);
        assertThat(error, connProps.isTestOnCreate(), is(equalTo(true)));
    }

    /**
     * testOnCreate test
     */
    @Test
    public void testOnCreateTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_CREATE, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "testOnCreate does not match expected default value";
        assertThat(error, connProps.isTestOnCreate(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_CREATE, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnCreate does not match expected value";
        assertThat(error, connProps.isTestOnCreate(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_CREATE, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnCreate does not match expected value";
        assertThat(error, connProps.isTestOnCreate(), is(equalTo(true)));
    }

    /**
     * default testOnBorrow test
     */
    @Test
    public void defaultTestOnBorrowTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "testOnBorrow does not match expected default value";
        assertThat(error, connProps.isTestOnBorrow(), is(equalTo(true)));
        error = "testOnBorrow does not match expected value";
        connProps.setTestOnBorrow(false);
        assertThat(error, connProps.isTestOnBorrow(), is(equalTo(false)));
    }

    /**
     * testOnBorrow test
     */
    @Test
    public void testOnBorrowTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_BORROW, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "testOnBorrow does not match expected default value";
        assertThat(error, connProps.isTestOnBorrow(), is(equalTo(true)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_BORROW, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnBorrow does not match expected value";
        assertThat(error, connProps.isTestOnBorrow(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_BORROW, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnBorrow does not match expected value";
        assertThat(error, connProps.isTestOnBorrow(), is(equalTo(true)));
    }

    /**
     * default testOnReturn test
     */
    @Test
    public void defaultTestOnReturnTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "testOnReturn does not match expected default value";
        assertThat(error, connProps.isTestOnReturn(), is(equalTo(false)));
        error = "testOnReturn does not match expected value";
        connProps.setTestOnReturn(true);
        assertThat(error, connProps.isTestOnReturn(), is(equalTo(true)));
    }

    /**
     * testOnReturn test
     */
    @Test
    public void testOnReturnTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_RETURN, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "testOnReturn does not match expected default value";
        assertThat(error, connProps.isTestOnReturn(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_RETURN, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnReturn does not match expected value";
        assertThat(error, connProps.isTestOnReturn(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_ON_RETURN, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testOnReturn does not match expected value";
        assertThat(error, connProps.isTestOnReturn(), is(equalTo(true)));
    }

    /**
     * default testWhileIdle test
     */
    @Test
    public void defaultTestWhileIdleTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "testWhileIdle does not match expected default value";
        assertThat(error, connProps.isTestWhileIdle(), is(equalTo(false)));
        error = "testWhileIdle does not match expected value";
        connProps.setTestWhileIdle(true);
        assertThat(error, connProps.isTestWhileIdle(), is(equalTo(true)));
    }

    /**
     * testWhileIdle test
     */
    @Test
    public void testWhileIdleTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TEST_WHILE_IDLE, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "testWhileIdle does not match expected default value";
        assertThat(error, connProps.isTestWhileIdle(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_WHILE_IDLE, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testWhileIdle does not match expected value";
        assertThat(error, connProps.isTestWhileIdle(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_TEST_WHILE_IDLE, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "testWhileIdle does not match expected value";
        assertThat(error, connProps.isTestWhileIdle(), is(equalTo(true)));
    }

    /**
     * default timeBetweenEvictionRunsMillis test
     */
    @Test
    public void defaultTimeBetweenEvictionRunsMillisTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "timeBetweenEvictionRunsMillis does not match expected default value";
        assertThat(error, connProps.getTimeBetweenEvictionRunsMillis(), is(equalTo(-1l)));
        error = "timeBetweenEvictionRunsMillis does not match expected value";
        connProps.setTimeBetweenEvictionRunsMillis(42);
        assertThat(error, connProps.getTimeBetweenEvictionRunsMillis(), is(equalTo(42l)));
    }

    /**
     * timeBetweenEvictionRunsMillis test
     */
    @Test
    public void timeBetweenEvictionRunsMillisTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "timeBetweenEvictionRunsMillis does not match expected default value";
        assertThat(error, connProps.getTimeBetweenEvictionRunsMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "timeBetweenEvictionRunsMillis does not match expected value";
        assertThat(error, connProps.getTimeBetweenEvictionRunsMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "timeBetweenEvictionRunsMillis does not match expected value";
        assertThat(error, connProps.getTimeBetweenEvictionRunsMillis(), is(equalTo(42l)));
    }

    /**
     * default numTestsPerEvictionRun test
     */
    @Test
    public void defaultNumTestsPerEvictionRunTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "numTestsPerEvictionRun does not match expected default value";
        assertThat(error, connProps.getNumTestsPerEvictionRun(), is(equalTo(3)));
        error = "numTestsPerEvictionRun does not match expected value";
        connProps.setNumTestsPerEvictionRun(42);
        assertThat(error, connProps.getNumTestsPerEvictionRun(), is(equalTo(42)));
    }

    /**
     * numTestsPerEvictionRun test
     */
    @Test
    public void numTestsPerEvictionRunTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_NUM_TESTS_PER_EVICITON_RUN, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "numTestsPerEvictionRun does not match expected default value";
        assertThat(error, connProps.getNumTestsPerEvictionRun(), is(equalTo(3)));

        map.put(MapBasedConnPropsBuilder.KEY_NUM_TESTS_PER_EVICITON_RUN, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "numTestsPerEvictionRun does not match expected value";
        assertThat(error, connProps.getNumTestsPerEvictionRun(), is(equalTo(3)));

        map.put(MapBasedConnPropsBuilder.KEY_NUM_TESTS_PER_EVICITON_RUN, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "numTestsPerEvictionRun does not match expected value";
        assertThat(error, connProps.getNumTestsPerEvictionRun(), is(equalTo(42)));
    }

    /**
     * illegal numTestsPerEvictionRun test
     */
    @Test(expected = IllegalArgumentException.class)
    public void numTestsPerEvictionRunIllegalTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        connProps.setNumTestsPerEvictionRun(0);
    }

    /**
     * default minEvictableIdleTimeMillis test
     */
    @Test
    public void defaultMinEvictableIdleTimeMillisTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "minEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, connProps.getMinEvictableIdleTimeMillis(), is(equalTo(1000 * 60 * 30l)));
        error = "minEvictableIdleTimeMillis does not match expected value";
        connProps.setMinEvictableIdleTimeMillis(42);
        assertThat(error, connProps.getMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * minEvictableIdleTimeMillis test
     */
    @Test
    public void minEvictableIdleTimeMillisTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "minEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, connProps.getMinEvictableIdleTimeMillis(), is(equalTo(1000 * 60 * 30l)));

        map.put(MapBasedConnPropsBuilder.KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "minEvictableIdleTimeMillis does not match expected value";
        assertThat(error, connProps.getMinEvictableIdleTimeMillis(), is(equalTo(1000 * 60 * 30l)));

        map.put(MapBasedConnPropsBuilder.KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "minEvictableIdleTimeMillis does not match expected value";
        assertThat(error, connProps.getMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * default softMinEvictableIdleTimeMillis test
     */
    @Test
    public void defaultSoftMinEvictableIdleTimeMillisTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "softMinEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, connProps.getSoftMinEvictableIdleTimeMillis(), is(equalTo(-1l)));
        error = "softMinEvictableIdleTimeMillis does not match expected value";
        connProps.setSoftMinEvictableIdleTimeMillis(42);
        assertThat(error, connProps.getSoftMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * softMinEvictableIdleTimeMillis test
     */
    @Test
    public void softMinEvictableIdleTimeMillisTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "softMinEvictableIdleTimeMillis does not match expected default value";
        assertThat(error, connProps.getSoftMinEvictableIdleTimeMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "softMinEvictableIdleTimeMillis does not match expected value";
        assertThat(error, connProps.getSoftMinEvictableIdleTimeMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "softMinEvictableIdleTimeMillis does not match expected value";
        assertThat(error, connProps.getSoftMinEvictableIdleTimeMillis(), is(equalTo(42l)));
    }

    /**
     * default lifo test
     */
    @Test
    public void defaultLifoTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "lifo does not match expected default value";
        assertThat(error, connProps.isLifo(), is(equalTo(true)));
        error = "lifo does not match expected value";
        connProps.setLifo(false);
        assertThat(error, connProps.isLifo(), is(equalTo(false)));
    }

    /**
     * lifo test
     */
    @Test
    public void lifoTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_LIFO, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "lifo does not match expected default value";
        assertThat(error, connProps.isLifo(), is(equalTo(true)));

        map.put(MapBasedConnPropsBuilder.KEY_LIFO, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "lifo does not match expected value";
        assertThat(error, connProps.isLifo(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_LIFO, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "lifo does not match expected value";
        assertThat(error, connProps.isLifo(), is(equalTo(true)));
    }

    /**
     * defaultAutoCommit test
     */
    @Test
    public void defaultDefaultAutoCommitTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "defaultAutoCommit does not match expected default value";
        assertThat(error, connProps.isDefaultAutoCommit(), is(equalTo(true)));
        error = "defaultAutoCommit does not match expected value";
        connProps.setDefaultAutoCommit(false);
        assertThat(error, connProps.isDefaultAutoCommit(), is(equalTo(false)));
    }

    /**
     * defaultAutoCommit test
     */
    @Test
    public void defaultAutoCommitTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_AUTO_COMMIT, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "defaultAutoCommit does not match expected default value";
        assertThat(error, connProps.isDefaultAutoCommit(), is(equalTo(true)));

        map.put(MapBasedConnPropsBuilder.KEY_AUTO_COMMIT, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultAutoCommit does not match expected value";
        assertThat(error, connProps.isDefaultAutoCommit(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_AUTO_COMMIT, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultAutoCommit does not match expected value";
        assertThat(error, connProps.isDefaultAutoCommit(), is(equalTo(true)));
    }

    /**
     * default defaultReadOnly test
     */
    @Test
    public void defaultDefaultReadOnlyTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "defaultReadOnly does not match expected default value";
        assertThat(error, connProps.isDefaultReadOnly(), is(equalTo(false)));
        error = "defaultReadOnly does not match expected value";
        connProps.setDefaultReadOnly(true);
        assertThat(error, connProps.isDefaultReadOnly(), is(equalTo(true)));
    }

    /**
     * defaultReadOnly test
     */
    @Test
    public void defaultReadOnlyTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_READ_ONLY, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "defaultReadOnly does not match expected default value";
        assertThat(error, connProps.isDefaultReadOnly(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_READ_ONLY, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultReadOnly does not match expected value";
        assertThat(error, connProps.isDefaultReadOnly(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_READ_ONLY, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultReadOnly does not match expected value";
        assertThat(error, connProps.isDefaultReadOnly(), is(equalTo(true)));
    }

    /**
     * default defaultTransactionIsolation test
     */
    @Test
    public void defaultDefaultTransactionIsolationTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "defaultTransactionIsolation does not match expected default value";
        assertThat(error, connProps.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_REPEATABLE_READ)));
        error = "defaultTransactionIsolation does not match expected value";
        connProps.setDefaultTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        assertThat(error, connProps.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_SERIALIZABLE)));
    }

    /**
     * defaultTransactionIsolation test
     */
    @Test
    public void defaultTransactionIsolationTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_TRANSACTION_ISOLATION, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "defaultTransactionIsolation does not match expected default value";
        assertThat(error, connProps.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_REPEATABLE_READ)));

        map.put(MapBasedConnPropsBuilder.KEY_TRANSACTION_ISOLATION, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultTransactionIsolation does not match expected value";
        assertThat(error, connProps.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_REPEATABLE_READ)));

        map.put(MapBasedConnPropsBuilder.KEY_TRANSACTION_ISOLATION, String.valueOf(Connection.TRANSACTION_SERIALIZABLE));
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "defaultTransactionIsolation does not match expected value";
        assertThat(error, connProps.getDefaultTransactionIsolation(), is(equalTo(Connection.TRANSACTION_SERIALIZABLE)));
    }

    /**
     * default cacheState test
     */
    @Test
    public void defaultCacheStateTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "cacheState does not match expected default value";
        assertThat(error, connProps.isCacheState(), is(equalTo(true)));
        error = "cacheState does not match expected value";
        connProps.setCacheState(false);
        assertThat(error, connProps.isCacheState(), is(equalTo(false)));
    }

    /**
     * cacheState test
     */
    @Test
    public void cacheStateTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_CACHE_STATE, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "cacheState does not match expected default value";
        assertThat(error, connProps.isCacheState(), is(equalTo(true)));

        map.put(MapBasedConnPropsBuilder.KEY_CACHE_STATE, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "cacheState does not match expected value";
        assertThat(error, connProps.isCacheState(), is(equalTo(false)));

        map.put(MapBasedConnPropsBuilder.KEY_CACHE_STATE, "tRuE");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "cacheState does not match expected value";
        assertThat(error, connProps.isCacheState(), is(equalTo(true)));
    }

    /**
     * default validationQuery test
     */
    @Test
    public void defaultValidationQueryTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "validationQuery does not match expected default value";
        assertThat(error, connProps.getValidationQuery(), is(equalTo("SELECT 1")));
        error = "validationQuery does not match expected value";
        connProps.setValidationQuery("42");
        assertThat(error, connProps.getValidationQuery(), is(equalTo("42")));
    }

    /**
     * validationQuery test
     */
    @Test
    public void validationQueryTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_VALIDATION_QUERY, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "validationQuery does not match expected default value";
        assertThat(error, connProps.getValidationQuery(), is(equalTo("SELECT 1")));

        map.put(MapBasedConnPropsBuilder.KEY_VALIDATION_QUERY, "");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "validationQuery does not match expected value";
        assertThat(error, connProps.getValidationQuery(), is(equalTo("SELECT 1")));

        map.put(MapBasedConnPropsBuilder.KEY_VALIDATION_QUERY, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "validationQuery does not match expected default value";
        assertThat(error, connProps.getValidationQuery(), is(equalTo("42")));
    }

    /**
     * default maxConnLifetimeMillis test
     */
    @Test
    public void defaultMaxConnLifetimeMillisTest() {

        ConnectionProperties connProps = MapBasedConnPropsBuilder.buildDefault();

        String error = "maxConnLifetimeMillis does not match expected default value";
        assertThat(error, connProps.getMaxConnLifetimeMillis(), is(equalTo(-1l)));
        error = "maxConnLifetimeMillis does not match expected value";
        connProps.setMaxConnLifetimeMillis(42);
        assertThat(error, connProps.getMaxConnLifetimeMillis(), is(equalTo(42l)));
    }

    /**
     * maxConnLifetimeMillis test
     */
    @Test
    public void maxConnLifetimeMillisTest() {

        Map<String, String> map = new HashMap<>();

        map.put(MapBasedConnPropsBuilder.KEY_MAX_CONN_LIFETIME_MILLIS, null);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);
        String error = "maxConnLifetimeMillis does not match expected default value";
        assertThat(error, connProps.getMaxConnLifetimeMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_CONN_LIFETIME_MILLIS, "asdf");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxConnLifetimeMillis does not match expected value";
        assertThat(error, connProps.getMaxConnLifetimeMillis(), is(equalTo(-1l)));

        map.put(MapBasedConnPropsBuilder.KEY_MAX_CONN_LIFETIME_MILLIS, "42");
        connProps = MapBasedConnPropsBuilder.build(map);
        error = "maxConnLifetimeMillis does not match expected value";
        assertThat(error, connProps.getMaxConnLifetimeMillis(), is(equalTo(42l)));
    }

    /**
     * additionalProperties test: make sure that the additional properties are being set
     */
    @Test
    public void additionalPropertiesTest() {

        String key = "some property";
        String value = "some value";

        Map<String, String> map = new HashMap<>();

        map.put(key, value);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);

        String error = "Additional properties have not been set";
        assertThat(error, connProps.getAdditionalProperties(), is(notNullValue()));

        error = "Additional properties have not been set correctly";
        assertThat(error, connProps.getAdditionalProperties().get(key), is(equalTo(value)));
    }

    /**
     * additionalProperties test: make sure that the additional properties are being set to a new object (i.e. a
     * defensive copy is being made)
     */
    @Test
    public void additionalPropertiesNoSingletonTest() {

        String key = "some property";
        String value = "some value";

        Map<String, String> map = new HashMap<>();

        map.put(key, value);
        ConnectionProperties connProps = MapBasedConnPropsBuilder.build(map);

        String error = "The properties builder returns a singleton";
        assertThat(error, map, is(not(sameInstance(connProps.getAdditionalProperties()))));
    }
}
