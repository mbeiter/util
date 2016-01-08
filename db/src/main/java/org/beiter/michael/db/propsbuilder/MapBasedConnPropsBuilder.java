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
package org.beiter.michael.db.propsbuilder;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.beiter.michael.db.ConnectionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class builds a set of {@link ConnectionProperties} using the settings obtained from a Map.
 * <p>
 * Use the keys from the various KEY_* fields to properly populate the Map.
 */
// CHECKSTYLE:OFF
// this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
// suppress warnings about the long variable names that are "inherited" from Apache DBCP (which I used as a blueprint)
// suppress warnings about a potential God class (not the case, this is triggered by the builder)
@SuppressWarnings({"PMD.LongVariable", "PMD.GodClass"})
// CHECKSTYLE:ON
public final class MapBasedConnPropsBuilder {

    /**
     * The logger object for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(MapBasedConnPropsBuilder.class);

    // #################
    // # Default values
    // #################

    /**
     * @see ConnectionProperties#setDriver(String)
     */
    public static final String DEFAULT_DRIVER = null;

    /**
     * @see ConnectionProperties#setUrl(String)
     */
    public static final String DEFAULT_URL = null;

    /**
     * @see ConnectionProperties#setUrl(String)
     */
    public static final String DEFAULT_USERNAME = null;

    /**
     * @see ConnectionProperties#setPassword(String)
     */
    public static final String DEFAULT_PASSWORD = null;

    /**
     * @see ConnectionProperties#setMaxTotal(int)
     */
    public static final int DEFAULT_MAX_TOTAL = 8;

    /**
     * @see ConnectionProperties#setMaxIdle(int)
     */
    public static final int DEFAULT_MAX_IDLE = 8;

    /**
     * @see ConnectionProperties#setMinIdle(int)
     */
    public static final int DEFAULT_MIN_IDLE = 0;

    /**
     * @see ConnectionProperties#setMaxWaitMillis(long)
     */
    public static final long DEFAULT_MAX_WAIT_MILLIS = -1;

    /**
     * @see ConnectionProperties#setTestOnCreate(boolean)
     */
    public static final boolean DEFAULT_TEST_ON_CREATE = false;

    /**
     * @see ConnectionProperties#setTestOnBorrow(boolean)
     */
    public static final boolean DEFAULT_TEST_ON_BORROW = true;

    /**
     * @see ConnectionProperties#setTestOnReturn(boolean)
     */
    public static final boolean DEFAULT_TEST_ON_RETURN = false;

    /**
     * @see ConnectionProperties#setTestWhileIdle(boolean)
     */
    public static final boolean DEFAULT_TEST_WHILE_IDLE = false;

    /**
     * @see ConnectionProperties#setTimeBetweenEvictionRunsMillis(long)
     */
    public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = -1;

    /**
     * @see ConnectionProperties#setNumTestsPerEvictionRun(int)
     */
    public static final int DEFAULT_NUM_TESTS_PER_EVICITON_RUN = 3;

    /**
     * @see ConnectionProperties#setMinEvictableIdleTimeMillis(long)
     */
    public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 1000 * 60 * 30;

    /**
     * @see ConnectionProperties#setSoftMinEvictableIdleTimeMillis(long)
     */
    public static final long DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS = -1;

    /**
     * @see ConnectionProperties#setLifo(boolean)
     */
    public static final boolean DEFAULT_LIFO = true;

    /**
     * @see ConnectionProperties#setDefaultAutoCommit(boolean)
     */
    public static final boolean DEFAULT_AUTO_COMMIT = true;

    /**
     * @see ConnectionProperties#setDefaultReadOnly(boolean)
     */
    public static final boolean DEFAULT_READ_ONLY = false;

    /**
     * @see ConnectionProperties#setDefaultTransactionIsolation(int)
     */
    public static final int DEFAULT_TRANSACTION_ISOLATION = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * @see ConnectionProperties#setCacheState(boolean)
     */
    public static final boolean DEFAULT_CACHE_STATE = true;

    /**
     * @see ConnectionProperties#setValidationQuery(String)
     */
    public static final String DEFAULT_VALIDATION_QUERY = "SELECT 1";

    /**
     * @see ConnectionProperties#setMaxConnLifetimeMillis(long)
     */
    public static final long DEFAULT_MAX_CONN_LIFETIME_MILLIS = -1;


    // #####################
    // # Configuration Keys
    // #####################

    /**
     * @see ConnectionProperties#setDriver(String)
     */
    public static final String KEY_DRIVER = "jdbc.connection.driver";

    /**
     * @see ConnectionProperties#setUrl(String)
     */
    public static final String KEY_URL = "jdbc.connection.url";

    /**
     * @see ConnectionProperties#setUsername(String)
     */
    public static final String KEY_USERNAME = "jdbc.connection.username";

    /**
     * @see ConnectionProperties#setPassword(String)
     */
    // Fortify will report a violation here for handling a hardcoded password, which is not the case.
    // This is a non-issue / false positive.
    public static final String KEY_PASSWORD = "jdbc.connection.password";

    /**
     * @see ConnectionProperties#setMaxTotal(int)
     */
    public static final String KEY_MAX_TOTAL = "jdbc.connection.maxTotal";

    /**
     * @see ConnectionProperties#setMaxIdle(int)
     */
    public static final String KEY_MAX_IDLE = "jdbc.connection.maxIdle";

    /**
     * @see ConnectionProperties#setMinIdle(int)
     */
    public static final String KEY_MIN_IDLE = "jdbc.connection.minIdle";

    /**
     * @see ConnectionProperties#setMaxWaitMillis(long)
     */
    public static final String KEY_MAX_WAIT_MILLIS = "jdbc.connection.maxWaitMillis";

    /**
     * @see ConnectionProperties#setTestOnCreate(boolean)
     */
    public static final String KEY_TEST_ON_CREATE = "jdbc.connection.testOnCreate";

    /**
     * @see ConnectionProperties#setTestOnBorrow(boolean)
     */
    public static final String KEY_TEST_ON_BORROW = "jdbc.connection.testOnBorrow";

    /**
     * @see ConnectionProperties#setTestOnReturn(boolean)
     */
    public static final String KEY_TEST_ON_RETURN = "jdbc.connection.testOnReturn";

    /**
     * @see ConnectionProperties#setTestWhileIdle(boolean)
     */
    public static final String KEY_TEST_WHILE_IDLE = "jdbc.connection.testWhileIdle";

    /**
     * @see ConnectionProperties#setTimeBetweenEvictionRunsMillis(long)
     */
    public static final String KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS = "jdbc.connection.timeBetweenEvictionRuns";

    /**
     * @see ConnectionProperties#setNumTestsPerEvictionRun(int)
     */
    public static final String KEY_NUM_TESTS_PER_EVICITON_RUN = "jdbc.connection.numTestsPerEvictionRun";

    /**
     * @see ConnectionProperties#setMinEvictableIdleTimeMillis(long)
     */
    public static final String KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS = "jdbc.connection.minEvictableTimeMillis";

    /**
     * @see ConnectionProperties#setSoftMinEvictableIdleTimeMillis(long)
     */
    public static final String KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS =
            "jdbc.connection.softMinEvictableIdleTimeMillis";

    /**
     * @see ConnectionProperties#setLifo(boolean)
     */
    public static final String KEY_LIFO = "jdbc.connection.lifo";

    /**
     * @see ConnectionProperties#setDefaultAutoCommit(boolean)
     */
    public static final String KEY_AUTO_COMMIT = "jdbc.connection.autoCommit";

    /**
     * @see ConnectionProperties#setDefaultReadOnly(boolean)
     */
    public static final String KEY_READ_ONLY = "jdbc.connection.readOnly";

    /**
     * @see ConnectionProperties#setDefaultTransactionIsolation(int)
     */
    public static final String KEY_TRANSACTION_ISOLATION = "jdbc.connection.transactionIsolation";

    /**
     * @see ConnectionProperties#setCacheState(boolean)
     */
    public static final String KEY_CACHE_STATE = "jdbc.connection.cacheState";

    /**
     * @see ConnectionProperties#setValidationQuery(String)
     */
    public static final String KEY_VALIDATION_QUERY = "jdbc.connection.validationQuery";

    /**
     * @see ConnectionProperties#setMaxConnLifetimeMillis(long)
     */
    public static final String KEY_MAX_CONN_LIFETIME_MILLIS = "jdbc.connection.maxConnLifetimeMillis";

    /**
     * A private constructor to prevent instantiation of this class
     */
    private MapBasedConnPropsBuilder() {
    }

    /**
     * Creates a set of connection properties that use the defaults as specified in this class.
     *
     * @return A set of connection properties with (reasonable) defaults
     * @see MapBasedConnPropsBuilder
     */
    public static ConnectionProperties buildDefault() {

        return MapBasedConnPropsBuilder.build(new ConcurrentHashMap<String, String>());
    }

    /**
     * Initialize a set of connection properties based on key / values in a <code>HashMap</code>.
     *
     * @param properties A <code>HashMap</code> with configuration properties, using the keys as specified in this class
     * @return A <code>ConnectionProperties</code> object with default values, plus the provided parameters
     * @throws NullPointerException When {@code properties} is {@code null}
     */
    // CHECKSTYLE:OFF
    // this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
    // suppress warnings about this method being too long (not much point in splitting up this one!)
    // suppress warnings about this method being too complex (can't extract a generic subroutine to reduce exec paths)
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "PMD.NcssMethodCount", "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
    // CHECKSTYLE:ON
    public static ConnectionProperties build(final Map<String, String> properties) {

        Validate.notNull(properties, "The validated object 'properties' is null");

        final ConnectionProperties connProps = new ConnectionProperties();
        String tmp = properties.get(KEY_DRIVER);
        if (StringUtils.isNotEmpty(tmp)) { // driver cannot be blank, defaulting to null to catch it
            connProps.setDriver(tmp);
            logValue(KEY_DRIVER, tmp);
        } else {
            connProps.setDriver(DEFAULT_DRIVER);
            logDefault(KEY_DRIVER, DEFAULT_DRIVER);
        }

        tmp = properties.get(KEY_URL);
        if (StringUtils.isNotEmpty(tmp)) { // url cannot be blank, defaulting to null to catch it
            connProps.setUrl(tmp);
            logValue(KEY_URL, tmp);
        } else {
            connProps.setUrl(DEFAULT_URL);
            logDefault(KEY_URL, DEFAULT_URL);
        }

        tmp = properties.get(KEY_USERNAME);
        if (tmp == null) { // username may be a blank string, but not null
            connProps.setUsername(DEFAULT_USERNAME);
            logDefault(KEY_USERNAME, DEFAULT_USERNAME);
        } else {
            connProps.setUsername(tmp);
            logValue(KEY_USERNAME, tmp);
        }

        tmp = properties.get(KEY_PASSWORD);
        if (tmp == null) { // password may be a blank string, but not null
            connProps.setPassword(DEFAULT_PASSWORD);
            logDefault(KEY_PASSWORD, DEFAULT_PASSWORD);
        } else {
            connProps.setPassword(tmp);
            logValue(KEY_PASSWORD, tmp);
        }

        tmp = properties.get(KEY_MAX_TOTAL);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMaxTotal(Integer.decode(tmp));
                logValue(KEY_MAX_TOTAL, tmp);
            } else {
                connProps.setMaxTotal(DEFAULT_MAX_TOTAL);
                logDefault(KEY_MAX_TOTAL, tmp, "not numeric", String.valueOf(DEFAULT_MAX_TOTAL));
            }
        } else {
            connProps.setMaxTotal(DEFAULT_MAX_TOTAL);
            logDefault(KEY_MAX_TOTAL, String.valueOf(DEFAULT_MAX_TOTAL));
        }

        tmp = properties.get(KEY_MAX_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMaxIdle(Integer.decode(tmp));
                logValue(KEY_MAX_IDLE, tmp);
            } else {
                connProps.setMaxIdle(DEFAULT_MAX_IDLE);
                logDefault(KEY_MAX_IDLE, tmp, "not numeric", String.valueOf(DEFAULT_MAX_IDLE));
            }
        } else {
            connProps.setMaxIdle(DEFAULT_MAX_IDLE);
            logDefault(KEY_MAX_IDLE, String.valueOf(DEFAULT_MAX_IDLE));
        }

        tmp = properties.get(KEY_MIN_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMinIdle(Integer.decode(tmp));
                logValue(KEY_MIN_IDLE, tmp);
            } else {
                connProps.setMinIdle(DEFAULT_MIN_IDLE);
                logDefault(KEY_MIN_IDLE, tmp, "not numeric", String.valueOf(DEFAULT_MIN_IDLE));
            }
        } else {
            connProps.setMinIdle(DEFAULT_MIN_IDLE);
            logDefault(KEY_MIN_IDLE, String.valueOf(DEFAULT_MIN_IDLE));
        }

        tmp = properties.get(KEY_MAX_WAIT_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMaxWaitMillis(Long.decode(tmp));
                logValue(KEY_MAX_WAIT_MILLIS, tmp);
            } else {
                connProps.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
                logDefault(KEY_MAX_WAIT_MILLIS, tmp, "not numeric", String.valueOf(DEFAULT_MAX_WAIT_MILLIS));
            }
        } else {
            connProps.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
            logDefault(KEY_MAX_WAIT_MILLIS, String.valueOf(DEFAULT_MAX_WAIT_MILLIS));
        }

        tmp = properties.get(KEY_TEST_ON_CREATE);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setTestOnCreate(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_CREATE, tmp);
        } else {
            connProps.setTestOnCreate(DEFAULT_TEST_ON_CREATE);
            logDefault(KEY_TEST_ON_CREATE, String.valueOf(DEFAULT_TEST_ON_CREATE));
        }

        tmp = properties.get(KEY_TEST_ON_BORROW);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setTestOnBorrow(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_BORROW, tmp);
        } else {
            connProps.setTestOnBorrow(DEFAULT_TEST_ON_BORROW);
            logDefault(KEY_TEST_ON_BORROW, String.valueOf(DEFAULT_TEST_ON_BORROW));
        }

        tmp = properties.get(KEY_TEST_ON_RETURN);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setTestOnReturn(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_RETURN, tmp);
        } else {
            connProps.setTestOnReturn(DEFAULT_TEST_ON_RETURN);
            logDefault(KEY_TEST_ON_RETURN, String.valueOf(DEFAULT_TEST_ON_RETURN));
        }

        tmp = properties.get(KEY_TEST_WHILE_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setTestWhileIdle(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_WHILE_IDLE, tmp);
        } else {
            connProps.setTestWhileIdle(DEFAULT_TEST_WHILE_IDLE);
            logDefault(KEY_TEST_WHILE_IDLE, String.valueOf(DEFAULT_TEST_WHILE_IDLE));
        }

        tmp = properties.get(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setTimeBetweenEvictionRunsMillis(Long.decode(tmp));
                logValue(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, tmp);
            } else {
                connProps.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
                logDefault(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
            }
        } else {
            connProps.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
            logDefault(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS,
                    String.valueOf(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
        }

        tmp = properties.get(KEY_NUM_TESTS_PER_EVICITON_RUN);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setNumTestsPerEvictionRun(Integer.decode(tmp));
                logValue(KEY_NUM_TESTS_PER_EVICITON_RUN, tmp);
            } else {
                connProps.setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICITON_RUN);
                logDefault(KEY_NUM_TESTS_PER_EVICITON_RUN, tmp, "not numeric",
                        String.valueOf(DEFAULT_NUM_TESTS_PER_EVICITON_RUN));
            }
        } else {
            connProps.setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICITON_RUN);
            logDefault(KEY_NUM_TESTS_PER_EVICITON_RUN, String.valueOf(DEFAULT_NUM_TESTS_PER_EVICITON_RUN));
        }

        tmp = properties.get(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMinEvictableIdleTimeMillis(Long.decode(tmp));
                logValue(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp);
            } else {
                connProps.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
                logDefault(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
            }
        } else {
            connProps.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
            logDefault(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        }

        tmp = properties.get(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setSoftMinEvictableIdleTimeMillis(Long.decode(tmp));
                logValue(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp);
            } else {
                connProps.setSoftMinEvictableIdleTimeMillis(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
                logDefault(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
            }
        } else {
            connProps.setSoftMinEvictableIdleTimeMillis(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
            logDefault(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS,
                    String.valueOf(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        }

        tmp = properties.get(KEY_LIFO);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setLifo(Boolean.parseBoolean(tmp));
            logValue(KEY_LIFO, tmp);
        } else {
            connProps.setLifo(DEFAULT_LIFO);
            logDefault(KEY_LIFO, String.valueOf(DEFAULT_LIFO));
        }

        tmp = properties.get(KEY_AUTO_COMMIT);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setDefaultAutoCommit(Boolean.parseBoolean(tmp));
            logValue(KEY_AUTO_COMMIT, tmp);
        } else {
            connProps.setDefaultAutoCommit(DEFAULT_AUTO_COMMIT);
            logDefault(KEY_AUTO_COMMIT, String.valueOf(DEFAULT_AUTO_COMMIT));
        }

        tmp = properties.get(KEY_READ_ONLY);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setDefaultReadOnly(Boolean.parseBoolean(tmp));
            logValue(KEY_READ_ONLY, tmp);
        } else {
            connProps.setDefaultReadOnly(DEFAULT_READ_ONLY);
            logDefault(KEY_READ_ONLY, String.valueOf(DEFAULT_READ_ONLY));
        }

        tmp = properties.get(KEY_TRANSACTION_ISOLATION);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setDefaultTransactionIsolation(Integer.decode(tmp));
                logValue(KEY_TRANSACTION_ISOLATION, tmp);
            } else {
                connProps.setDefaultTransactionIsolation(DEFAULT_TRANSACTION_ISOLATION);
                logDefault(KEY_TRANSACTION_ISOLATION, tmp, "not numeric",
                        String.valueOf(DEFAULT_TRANSACTION_ISOLATION));
            }
        } else {
            connProps.setDefaultTransactionIsolation(DEFAULT_TRANSACTION_ISOLATION);
            logDefault(KEY_TRANSACTION_ISOLATION, String.valueOf(DEFAULT_TRANSACTION_ISOLATION));
        }

        tmp = properties.get(KEY_CACHE_STATE);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setCacheState(Boolean.parseBoolean(tmp));
            logValue(KEY_CACHE_STATE, tmp);
        } else {
            connProps.setCacheState(DEFAULT_CACHE_STATE);
            logDefault(KEY_CACHE_STATE, String.valueOf(DEFAULT_CACHE_STATE));
        }

        tmp = properties.get(KEY_VALIDATION_QUERY);
        if (StringUtils.isNotEmpty(tmp)) {
            connProps.setValidationQuery(tmp);
            logValue(KEY_VALIDATION_QUERY, tmp);
        } else {
            connProps.setValidationQuery(DEFAULT_VALIDATION_QUERY);
            logDefault(KEY_VALIDATION_QUERY, DEFAULT_VALIDATION_QUERY);
        }

        tmp = properties.get(KEY_MAX_CONN_LIFETIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                connProps.setMaxConnLifetimeMillis(Long.decode(tmp));
                logValue(KEY_MAX_CONN_LIFETIME_MILLIS, tmp);
            } else {
                connProps.setMaxConnLifetimeMillis(DEFAULT_MAX_CONN_LIFETIME_MILLIS);
                logDefault(KEY_MAX_CONN_LIFETIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_MAX_CONN_LIFETIME_MILLIS));
            }
        } else {
            connProps.setMaxConnLifetimeMillis(DEFAULT_MAX_CONN_LIFETIME_MILLIS);
            logDefault(KEY_MAX_CONN_LIFETIME_MILLIS, String.valueOf(DEFAULT_MAX_CONN_LIFETIME_MILLIS));
        }

        // set the additional properties, preserving the originally provided properties
        // create a defensive copy of the map and all its properties
        // the code looks a little complicated that "putAll()", but it catches situations where a Map is provided that
        // supports null values (e.g. a HashMap) vs Map implementations that do not (e.g. ConcurrentHashMap).
        final Map<String, String> tempMap = new ConcurrentHashMap<>();
        for (final Map.Entry<String, String> entry : properties.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();

            if (value != null) {
                tempMap.put(key, value);
            }
        }
        connProps.setAdditionalProperties(tempMap);

        return connProps;
    }

    /**
     * Create a log entry when a value has been successfully configured.
     *
     * @param key   The configuration key
     * @param value The value that is being used
     */
    private static void logValue(final String key, final String value) {

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // However, the configuration keys are not confidential, which makes this a non-issue / false positive.
        if (LOG.isInfoEnabled()) {
            final StringBuilder msg = new StringBuilder("Key found in configuration ('")
                    .append(key)
                    .append("'), using configured value (not disclosed here for security reasons)");
            LOG.info(msg.toString());
        }

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // The configuration VALUES are confidential. DO NOT activate DEBUG logging in production.
        if (LOG.isDebugEnabled()) {
            final StringBuilder msg = new StringBuilder("Key found in configuration ('")
                    .append(key)
                    .append("'), using configured value ('");
            if (value == null) {
                msg.append("null')");
            } else {
                msg.append(value).append("')");
            }
            LOG.debug(msg.toString());
        }
    }

    /**
     * Create a log entry when a default value is being used in case the propsbuilder key has not been provided in the
     * configuration.
     *
     * @param key          The configuration key
     * @param defaultValue The default value that is being used
     */
    private static void logDefault(final String key, final String defaultValue) {

        // Fortify will report a violation here because of disclosure of potentially confidential information.
        // However, neither the configuration keys nor the default propsbuilder values are confidential, which makes
        // this a non-issue / false positive.
        if (LOG.isInfoEnabled()) {
            final StringBuilder msg = new StringBuilder("Key is not configured ('")
                    .append(key)
                    .append("'), using default value ('");
            if (defaultValue == null) {
                msg.append("null')");
            } else {
                msg.append(defaultValue).append("')");
            }
            LOG.info(msg.toString());
        }
    }

    /**
     * Create a log entry when a default value is being used in case that an invalid configuration value has been
     * provided in the configuration for the propsbuilder key.
     *
     * @param key             The configuration key
     * @param invalidValue    The invalid value that cannot be used
     * @param validationError The validation error that caused the invalid value to be refused
     * @param defaultValue    The default value that is being used
     */
    // CHECKSTYLE:OFF
    // this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
    // suppress warnings about not using an object for the four strings in this PRIVATE method
    @SuppressWarnings({"PMD.UseObjectForClearerAPI"})
    // CHECKSTYLE:ON
    private static void logDefault(final String key,
                                   final String invalidValue,
                                   final String validationError,
                                   final String defaultValue) {

        if (LOG.isWarnEnabled()) {
            final StringBuilder msg = new StringBuilder("Invalid value ('")
                    .append(invalidValue)
                    .append("', ")
                    .append(validationError)
                    .append(") for key '")
                    .append(key)
                    .append("', using default instead ('");
            if (defaultValue == null) {
                msg.append("null')");
            } else {
                msg.append(defaultValue).append("')");
            }
            LOG.warn(msg.toString());
        }
    }
}
