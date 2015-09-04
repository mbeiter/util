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
 * <p/>
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
     */
    // CHECKSTYLE:OFF
    // this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
    // suppress warnings about this method being too long (not much point in splitting up this one!)
    // suppress warnings about this method being too complex (can't extract a generic subroutine to reduce exec paths)
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity", "PMD.NcssMethodCount", "PMD.CyclomaticComplexity", "PMD.StdCyclomaticComplexity", "PMD.ModifiedCyclomaticComplexity"})
    // CHECKSTYLE:ON
    public static ConnectionProperties build(final Map<String, String> properties) {

        Validate.notNull(properties);

        final ConnectionProperties poolSpec = new ConnectionProperties();
        String tmp = properties.get(KEY_DRIVER);
        if (StringUtils.isNotEmpty(tmp)) { // driver cannot be blank, defaulting to null to catch it
            poolSpec.setDriver(tmp);
            logValue(KEY_DRIVER, tmp);
        } else {
            poolSpec.setDriver(DEFAULT_DRIVER);
            logDefault(KEY_DRIVER, DEFAULT_DRIVER);
        }

        tmp = properties.get(KEY_URL);
        if (StringUtils.isNotEmpty(tmp)) { // url cannot be blank, defaulting to null to catch it
            poolSpec.setUrl(tmp);
            logValue(KEY_URL, tmp);
        } else {
            poolSpec.setUrl(DEFAULT_URL);
            logDefault(KEY_URL, DEFAULT_URL);
        }

        tmp = properties.get(KEY_USERNAME);
        if (tmp == null) { // username may be a blank string, but not null
            poolSpec.setUsername(DEFAULT_USERNAME);
            logDefault(KEY_USERNAME, DEFAULT_USERNAME);
        } else {
            poolSpec.setUsername(tmp);
            logValue(KEY_USERNAME, tmp);
        }

        tmp = properties.get(KEY_PASSWORD);
        if (tmp == null) { // password may be a blank string, but not null
            poolSpec.setPassword(DEFAULT_PASSWORD);
            logDefault(KEY_PASSWORD, DEFAULT_PASSWORD);
        } else {
            poolSpec.setPassword(tmp);
            logValue(KEY_PASSWORD, tmp);
        }

        tmp = properties.get(KEY_MAX_TOTAL);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMaxTotal(Integer.decode(tmp));
                logValue(KEY_MAX_TOTAL, tmp);
            } else {
                poolSpec.setMaxTotal(DEFAULT_MAX_TOTAL);
                logDefault(KEY_MAX_TOTAL, tmp, "not numeric", String.valueOf(DEFAULT_MAX_TOTAL));
            }
        } else {
            poolSpec.setMaxTotal(DEFAULT_MAX_TOTAL);
            logDefault(KEY_MAX_TOTAL, String.valueOf(DEFAULT_MAX_TOTAL));
        }

        tmp = properties.get(KEY_MAX_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMaxIdle(Integer.decode(tmp));
                logValue(KEY_MAX_IDLE, tmp);
            } else {
                poolSpec.setMaxIdle(DEFAULT_MAX_IDLE);
                logDefault(KEY_MAX_IDLE, tmp, "not numeric", String.valueOf(DEFAULT_MAX_IDLE));
            }
        } else {
            poolSpec.setMaxIdle(DEFAULT_MAX_IDLE);
            logDefault(KEY_MAX_IDLE, String.valueOf(DEFAULT_MAX_IDLE));
        }

        tmp = properties.get(KEY_MIN_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMinIdle(Integer.decode(tmp));
                logValue(KEY_MIN_IDLE, tmp);
            } else {
                poolSpec.setMinIdle(DEFAULT_MIN_IDLE);
                logDefault(KEY_MIN_IDLE, tmp, "not numeric", String.valueOf(DEFAULT_MIN_IDLE));
            }
        } else {
            poolSpec.setMinIdle(DEFAULT_MIN_IDLE);
            logDefault(KEY_MIN_IDLE, String.valueOf(DEFAULT_MIN_IDLE));
        }

        tmp = properties.get(KEY_MAX_WAIT_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMaxWaitMillis(Long.decode(tmp));
                logValue(KEY_MAX_WAIT_MILLIS, tmp);
            } else {
                poolSpec.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
                logDefault(KEY_MAX_WAIT_MILLIS, tmp, "not numeric", String.valueOf(DEFAULT_MAX_WAIT_MILLIS));
            }
        } else {
            poolSpec.setMaxWaitMillis(DEFAULT_MAX_WAIT_MILLIS);
            logDefault(KEY_MAX_WAIT_MILLIS, String.valueOf(DEFAULT_MAX_WAIT_MILLIS));
        }

        tmp = properties.get(KEY_TEST_ON_CREATE);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setTestOnCreate(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_CREATE, tmp);
        } else {
            poolSpec.setTestOnCreate(DEFAULT_TEST_ON_CREATE);
            logDefault(KEY_TEST_ON_CREATE, String.valueOf(DEFAULT_TEST_ON_CREATE));
        }

        tmp = properties.get(KEY_TEST_ON_BORROW);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setTestOnBorrow(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_BORROW, tmp);
        } else {
            poolSpec.setTestOnBorrow(DEFAULT_TEST_ON_BORROW);
            logDefault(KEY_TEST_ON_BORROW, String.valueOf(DEFAULT_TEST_ON_BORROW));
        }

        tmp = properties.get(KEY_TEST_ON_RETURN);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setTestOnReturn(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_ON_RETURN, tmp);
        } else {
            poolSpec.setTestOnReturn(DEFAULT_TEST_ON_RETURN);
            logDefault(KEY_TEST_ON_RETURN, String.valueOf(DEFAULT_TEST_ON_RETURN));
        }

        tmp = properties.get(KEY_TEST_WHILE_IDLE);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setTestWhileIdle(Boolean.parseBoolean(tmp));
            logValue(KEY_TEST_WHILE_IDLE, tmp);
        } else {
            poolSpec.setTestWhileIdle(DEFAULT_TEST_WHILE_IDLE);
            logDefault(KEY_TEST_WHILE_IDLE, String.valueOf(DEFAULT_TEST_WHILE_IDLE));
        }

        tmp = properties.get(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setTimeBetweenEvictionRunsMillis(Long.decode(tmp));
                logValue(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, tmp);
            } else {
                poolSpec.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
                logDefault(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
            }
        } else {
            poolSpec.setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
            logDefault(KEY_TIME_BETWEEN_EVICTION_RUNS_MILLIS,
                    String.valueOf(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
        }

        tmp = properties.get(KEY_NUM_TESTS_PER_EVICITON_RUN);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setNumTestsPerEvictionRun(Integer.decode(tmp));
                logValue(KEY_NUM_TESTS_PER_EVICITON_RUN, tmp);
            } else {
                poolSpec.setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICITON_RUN);
                logDefault(KEY_NUM_TESTS_PER_EVICITON_RUN, tmp, "not numeric",
                        String.valueOf(DEFAULT_NUM_TESTS_PER_EVICITON_RUN));
            }
        } else {
            poolSpec.setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICITON_RUN);
            logDefault(KEY_NUM_TESTS_PER_EVICITON_RUN, String.valueOf(DEFAULT_NUM_TESTS_PER_EVICITON_RUN));
        }

        tmp = properties.get(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMinEvictableIdleTimeMillis(Long.decode(tmp));
                logValue(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp);
            } else {
                poolSpec.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
                logDefault(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
            }
        } else {
            poolSpec.setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
            logDefault(KEY_MIN_EVICTABLE_IDLE_TIME_MILLIS, String.valueOf(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        }

        tmp = properties.get(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setSoftMinEvictableIdleTimeMillis(Long.decode(tmp));
                logValue(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp);
            } else {
                poolSpec.setSoftMinEvictableIdleTimeMillis(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
                logDefault(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
            }
        } else {
            poolSpec.setSoftMinEvictableIdleTimeMillis(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
            logDefault(KEY_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS,
                    String.valueOf(DEFAULT_SOFT_MIN_EVICTABLE_IDLE_TIME_MILLIS));
        }

        tmp = properties.get(KEY_LIFO);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setLifo(Boolean.parseBoolean(tmp));
            logValue(KEY_LIFO, tmp);
        } else {
            poolSpec.setLifo(DEFAULT_LIFO);
            logDefault(KEY_LIFO, String.valueOf(DEFAULT_LIFO));
        }

        tmp = properties.get(KEY_AUTO_COMMIT);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setDefaultAutoCommit(Boolean.parseBoolean(tmp));
            logValue(KEY_AUTO_COMMIT, tmp);
        } else {
            poolSpec.setDefaultAutoCommit(DEFAULT_AUTO_COMMIT);
            logDefault(KEY_AUTO_COMMIT, String.valueOf(DEFAULT_AUTO_COMMIT));
        }

        tmp = properties.get(KEY_READ_ONLY);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setDefaultReadOnly(Boolean.parseBoolean(tmp));
            logValue(KEY_READ_ONLY, tmp);
        } else {
            poolSpec.setDefaultReadOnly(DEFAULT_READ_ONLY);
            logDefault(KEY_READ_ONLY, String.valueOf(DEFAULT_READ_ONLY));
        }

        tmp = properties.get(KEY_TRANSACTION_ISOLATION);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setDefaultTransactionIsolation(Integer.decode(tmp));
                logValue(KEY_TRANSACTION_ISOLATION, tmp);
            } else {
                poolSpec.setDefaultTransactionIsolation(DEFAULT_TRANSACTION_ISOLATION);
                logDefault(KEY_TRANSACTION_ISOLATION, tmp, "not numeric",
                        String.valueOf(DEFAULT_TRANSACTION_ISOLATION));
            }
        } else {
            poolSpec.setDefaultTransactionIsolation(DEFAULT_TRANSACTION_ISOLATION);
            logDefault(KEY_TRANSACTION_ISOLATION, String.valueOf(DEFAULT_TRANSACTION_ISOLATION));
        }

        tmp = properties.get(KEY_CACHE_STATE);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setCacheState(Boolean.parseBoolean(tmp));
            logValue(KEY_CACHE_STATE, tmp);
        } else {
            poolSpec.setCacheState(DEFAULT_CACHE_STATE);
            logDefault(KEY_CACHE_STATE, String.valueOf(DEFAULT_CACHE_STATE));
        }

        tmp = properties.get(KEY_VALIDATION_QUERY);
        if (StringUtils.isNotEmpty(tmp)) {
            poolSpec.setValidationQuery(tmp);
            logValue(KEY_VALIDATION_QUERY, tmp);
        } else {
            poolSpec.setValidationQuery(DEFAULT_VALIDATION_QUERY);
            logDefault(KEY_VALIDATION_QUERY, DEFAULT_VALIDATION_QUERY);
        }

        tmp = properties.get(KEY_MAX_CONN_LIFETIME_MILLIS);
        if (StringUtils.isNotEmpty(tmp)) {
            if (StringUtils.isNumeric(tmp)) {
                poolSpec.setMaxConnLifetimeMillis(Long.decode(tmp));
                logValue(KEY_MAX_CONN_LIFETIME_MILLIS, tmp);
            } else {
                poolSpec.setMaxConnLifetimeMillis(DEFAULT_MAX_CONN_LIFETIME_MILLIS);
                logDefault(KEY_MAX_CONN_LIFETIME_MILLIS, tmp, "not numeric",
                        String.valueOf(DEFAULT_MAX_CONN_LIFETIME_MILLIS));
            }
        } else {
            poolSpec.setMaxConnLifetimeMillis(DEFAULT_MAX_CONN_LIFETIME_MILLIS);
            logDefault(KEY_MAX_CONN_LIFETIME_MILLIS, String.valueOf(DEFAULT_MAX_CONN_LIFETIME_MILLIS));
        }

        // set the default properties

        return poolSpec;
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
