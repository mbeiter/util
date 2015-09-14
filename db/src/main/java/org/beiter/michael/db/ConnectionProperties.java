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

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class specifies connection pool properties.
 */
// CHECKSTYLE:OFF
// this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
// suppress warnings about the constructor (required for producing java docs)
// suppress warnings about the number of fields
// suppress warnings about the long variable names that are "inherited" from Apache DBCP (which I used as a blueprint)
// suppress warnings about the excessive number of public elements (triggered by the many getters and setters)
// suppress warnings about a potential God class (not the case, this is triggered by the many getters and setters)
@SuppressWarnings({"PMD.UnnecessaryConstructor", "PMD.TooManyFields", "PMD.LongVariable", "PMD.ExcessivePublicCount", "PMD.GodClass"})
// CHECKSTYLE:ON
public class ConnectionProperties {

    /**
     * @see ConnectionProperties#setDriver(String)
     */
    private String driver;

    /**
     * @see ConnectionProperties#setUrl(String)
     */
    private String url;

    /**
     * @see ConnectionProperties#setUsername(String)
     */
    private String username;

    /**
     * @see ConnectionProperties#setPassword(String)
     */
    private String password;

    /**
     * @see ConnectionProperties#setMaxTotal(int)
     */
    private int maxTotal;

    /**
     * @see ConnectionProperties#setMaxIdle(int)
     */
    private int maxIdle;

    /**
     * @see ConnectionProperties#setMinIdle(int)
     */
    private int minIdle;

    /**
     * @see ConnectionProperties#setMaxWaitMillis(long)
     */
    private long maxWaitMillis;

    /**
     * @see ConnectionProperties#setTestOnCreate(boolean)
     */
    private boolean testOnCreate;

    /**
     * @see ConnectionProperties#setTestOnBorrow(boolean)
     */
    private boolean testOnBorrow;

    /**
     * @see ConnectionProperties#setTestOnReturn(boolean)
     */
    private boolean testOnReturn;

    /**
     * @see ConnectionProperties#setTestWhileIdle(boolean)
     */
    private boolean testWhileIdle;

    /**
     * @see ConnectionProperties#setTimeBetweenEvictionRunsMillis(long)
     */
    private long timeBetweenEvictionRunsMillis;

    /**
     * @see ConnectionProperties#setNumTestsPerEvictionRun(int)
     */
    private int numTestsPerEvictionRun;

    /**
     * @see ConnectionProperties#setMinEvictableIdleTimeMillis(long)
     */
    private long minEvictableIdleTimeMillis;

    /**
     * @see ConnectionProperties#setSoftMinEvictableIdleTimeMillis(long)
     */
    private long softMinEvictableIdleTimeMillis;

    /**
     * @see ConnectionProperties#setLifo(boolean)
     */
    private boolean lifo;

    /**
     * @see ConnectionProperties#setDefaultAutoCommit(boolean)
     */
    private boolean defaultAutoCommit;

    /**
     * @see ConnectionProperties#setDefaultReadOnly(boolean)
     */
    private boolean defaultReadOnly;

    /**
     * @see ConnectionProperties#setDefaultTransactionIsolation(int)
     */
    private int defaultTransactionIsolation;

    /**
     * @see ConnectionProperties#setCacheState(boolean)
     */
    private boolean cacheState;

    /**
     * @see ConnectionProperties#setValidationQuery(String)
     */
    private String validationQuery;

    /**
     * @see ConnectionProperties#setMaxConnLifetimeMillis(long)
     */
    private long maxConnLifetimeMillis;

    /**
     * @see ConnectionProperties#setAdditionalProperties(Map<String, String>)
     */
    private Map<String, String> additionalProperties = new ConcurrentHashMap<>();

    /**
     * Constructs an empty set of connection properties, with most values being set to <code>null</code>, 0, or empty
     * (depending on the type of the property). Usually this constructor is used if this configuration POJO is populated
     * in an automated fashion (e.g. injection). If you need to build them manually (possibly with defaults), use or
     * create a properties builder.
     * <p>
     * You can change the defaults with the setters. If you need more control over the pool than what is provided by
     * the available setters, consider using a JNDI controlled connection pool instead.
     *
     * @see org.beiter.michael.db.propsbuilder.MapBasedConnPropsBuilder#buildDefault()
     * @see org.beiter.michael.db.propsbuilder.MapBasedConnPropsBuilder#build(java.util.Map)
     */
    public ConnectionProperties() {

        // no code here, constructor just for java docs
    }

    /**
     * Creates a set of connection properties from an exist set of connection properties, making a defensive copy.
     *
     * @see ConnectionProperties()
     * @param properties The set of connection properties to copy
     */
    public ConnectionProperties(final ConnectionProperties properties) {

        this();

        setDriver(properties.getDriver());
        setUrl(properties.getUrl());
        setUsername(properties.getUsername());
        setPassword(properties.getPassword());
        setMaxTotal(properties.getMaxTotal());
        setMaxIdle(properties.getMaxIdle());
        setMinIdle(properties.getMinIdle());
        setMaxWaitMillis(properties.getMaxWaitMillis());
        setTestOnCreate(properties.isTestOnCreate());
        setTestOnBorrow(properties.isTestOnBorrow());
        setTestOnReturn(properties.isTestOnReturn());
        setTestWhileIdle(properties.isTestWhileIdle());
        setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        setNumTestsPerEvictionRun(properties.getNumTestsPerEvictionRun());
        setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        setSoftMinEvictableIdleTimeMillis(properties.getSoftMinEvictableIdleTimeMillis());
        setLifo(properties.isLifo());
        setDefaultAutoCommit(properties.isDefaultAutoCommit());
        setDefaultReadOnly(properties.isDefaultReadOnly());
        setDefaultTransactionIsolation(properties.getDefaultTransactionIsolation());
        setCacheState(properties.isCacheState());
        setValidationQuery(properties.getValidationQuery());
        setMaxConnLifetimeMillis(properties.getMaxConnLifetimeMillis());
        setAdditionalProperties(properties.getAdditionalProperties());
    }

    /**
     * @return The JDBC database driver class
     * @see ConnectionProperties#setDriver(String)
     */
    public final String getDriver() {

        // no need for defensive copies of String

        return driver;
    }

    /**
     * The JDBC database driver class
     *
     * @param driver The JDBC database driver class
     */
    public final void setDriver(final String driver) {

        // no need for validation, as we cannot possible validate all SQL driver names and null is allowed

        // no need for defensive copies of boolean

        this.driver = driver;
    }

    /**
     * @return The JDBC database URL of the form <code>jdbc:subprotocol:subname</code>
     * @see ConnectionProperties#setUrl(String)
     */
    public final String getUrl() {

        // no need for defensive copies of String

        return url;
    }

    /**
     * The JDBC database URL of the form <code>jdbc:subprotocol:subname</code>
     *
     * @param url The JDBC database URL
     */
    public final void setUrl(final String url) {

        // no need for validation, as we cannot possible validate all URL patterns and null is allowed

        // no need for defensive copies of boolean

        this.url = url;
    }

    /**
     * @return The username for the connection
     * @see ConnectionProperties#setUsername(String)
     */
    public final String getUsername() {

        // no need for defensive copies of String

        return username;
    }

    /**
     * The username for the connection
     *
     * @param username The username for the connection
     */
    public final void setUsername(final String username) {

        // no need for validation, as we cannot possible validate all username patterns and null is allowed

        // no need for defensive copies of boolean

        this.username = username;
    }

    /**
     * @return The password for the connection
     * @see ConnectionProperties#setUrl(String)
     */
    public final String getPassword() {

        // no need for defensive copies of String

        return password;
    }

    /**
     * The password for the connection
     *
     * @param password The password for the connection
     */
    public final void setPassword(final String password) {

        // no need for validation, as we cannot possible validate all password patterns and null is allowed

        // no need for defensive copies of boolean

        this.password = password;
    }

    /**
     * @return the maximum numbers of active connections
     * @see ConnectionProperties#setMaxTotal(int)
     */
    public final int getMaxTotal() {

        // no need for defensive copies of int

        return maxTotal;
    }

    /**
     * The maximum number of active connections that can be allocated from this pool at the same time, or negative
     * for no limit.
     *
     * @param maxTotal the maximum numbers of active connections
     */
    public final void setMaxTotal(final int maxTotal) {

        // no need for validation, as int cannot be null and all possible values are allowed
        // no need for defensive copies of int

        this.maxTotal = maxTotal;
    }

    /**
     * @return the maximum number of idle connections
     * @see ConnectionProperties#setMaxIdle(int)
     */
    public final int getMaxIdle() {

        // no need for defensive copies of int

        return maxIdle;
    }

    /**
     * The maximum number of connections that can remain idle in the pool, without extra ones being released, or
     * negative for no limit.
     *
     * @param maxIdle the maximum number of idle connections
     */
    public final void setMaxIdle(final int maxIdle) {

        // no need for validation, as int cannot be null and all possible values are allowed
        // no need for defensive copies of int

        this.maxIdle = maxIdle;
    }

    /**
     * @return the minimum number of idle connections
     * @see ConnectionProperties#setMinIdle(int)
     */
    public final int getMinIdle() {

        // no need for defensive copies of int

        return minIdle;
    }

    /**
     * The minimum number of connections that can remain idle in the pool, without extra ones being created, or zero
     * to create none.
     *
     * @param minIdle the minimum number of idle connections
     */
    public final void setMinIdle(final int minIdle) {

        Validate.inclusiveBetween(0, Integer.MAX_VALUE, minIdle);

        // no need for defensive copies of int

        this.minIdle = minIdle;
    }

    /**
     * @return the maximum number of milliseconds that the pool will wait for a connection
     * @see ConnectionProperties#setMaxWaitMillis(long)
     */
    public final long getMaxWaitMillis() {

        // no need for defensive copies of long

        return maxWaitMillis;
    }

    /**
     * The maximum number of milliseconds that the pool will wait (when there are no available connections) for a
     * connection to be returned before throwing an exception, or -1 to wait indefinitely.
     *
     * @param maxWaitMillis the maximum number of milliseconds that the pool will wait for a connection
     */
    public final void setMaxWaitMillis(final long maxWaitMillis) {

        Validate.inclusiveBetween(-1, Integer.MAX_VALUE, maxWaitMillis);

        // no need for defensive copies of long

        this.maxWaitMillis = maxWaitMillis;
    }

    /**
     * @return the indication of whether objects will be validated after creation
     * @see ConnectionProperties#setTestOnCreate(boolean)
     */
    public final boolean isTestOnCreate() {

        // no need for defensive copies of boolean

        return testOnCreate;
    }

    /**
     * The indication of whether objects will be validated after creation. If the object fails to validate, the borrow
     * attempt that triggered the object creation will fail.
     *
     * @param testOnCreate the indication of whether objects will be validated after creation
     */
    public final void setTestOnCreate(final boolean testOnCreate) {

        // no need for validation, as boolean cannot be null and all possible values are allowed
        // no need for defensive copies of boolean

        this.testOnCreate = testOnCreate;
    }

    /**
     * @return the indication of whether objects will be validated before being borrowed from the pool
     * @see ConnectionProperties#setTestOnBorrow(boolean)
     */
    public final boolean isTestOnBorrow() {

        // no need for defensive copies of boolean

        return testOnBorrow;
    }

    /**
     * The indication of whether objects will be validated before being borrowed from the pool. If the object fails to
     * validate, it will be dropped from the pool, and we will attempt to borrow another.
     *
     * @param testOnBorrow the indication of whether objects will be validated before being borrowed from the pool
     */
    public final void setTestOnBorrow(final boolean testOnBorrow) {

        // no need for validation, as boolean cannot be null and all possible values are allowed
        // no need for defensive copies of boolean

        this.testOnBorrow = testOnBorrow;
    }

    /**
     * @return the indication of whether objects will be validated before being returned to the pool
     * @see ConnectionProperties#setTestOnReturn(boolean)
     */
    public final boolean isTestOnReturn() {

        // no need for defensive copies of boolean

        return testOnReturn;
    }

    /**
     * The indication of whether objects will be validated before being returned to the pool.
     *
     * @param testOnReturn the indication of whether objects will be validated before being returned to the pool
     */
    public final void setTestOnReturn(final boolean testOnReturn) {

        // no need for validation, as boolean cannot be null and all possible values are allowed
        // no need for defensive copies of boolean

        this.testOnReturn = testOnReturn;
    }

    /**
     * @return the indication of whether objects will be validated by the idle object evictor (if any)
     * @see ConnectionProperties#setTestWhileIdle(boolean)
     */
    public final boolean isTestWhileIdle() {

        // no need for defensive copies of boolean

        return testWhileIdle;
    }

    /**
     * The indication of whether objects will be validated by the idle object evictor (if any). If an object fails
     * to validate, it will be dropped from the pool.
     *
     * @param testWhileIdle the indication of whether objects will be validated by the idle object evictor (if any)
     */
    public final void setTestWhileIdle(final boolean testWhileIdle) {

        // no need for validation, as boolean cannot be null and all possible values are allowed
        // no need for defensive copies of boolean

        this.testWhileIdle = testWhileIdle;
    }

    /**
     * @return the number of milliseconds to sleep between runs of the idle object evictor thread
     * @see ConnectionProperties#setTimeBetweenEvictionRunsMillis(long)
     */
    public final long getTimeBetweenEvictionRunsMillis() {

        // no need for defensive copies of long

        return timeBetweenEvictionRunsMillis;
    }

    /**
     * The number of milliseconds to sleep between runs of the idle object evictor thread. When non-positive, no idle
     * object evictor thread will be run.
     *
     * @param timeBetweenEvictionRunsMillis the number of milliseconds to sleep between runs of the idle object evictor
     *                                      thread
     */
    public final void setTimeBetweenEvictionRunsMillis(final long timeBetweenEvictionRunsMillis) {

        // no need for validation, as long cannot be null and all possible values are allowed
        // no need for defensive copies of long

        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    /**
     * @return the number of objects to examine during each run of the idle object evictor thread (if any)
     * @see ConnectionProperties#setNumTestsPerEvictionRun(int)
     */
    public final int getNumTestsPerEvictionRun() {

        // no need for defensive copies of int

        return numTestsPerEvictionRun;
    }

    /**
     * The number of objects to examine during each run of the idle object evictor thread (if any).
     *
     * @param numTestsPerEvictionRun the number of objects to examine during each run of the idle object evictor thread
     *                               (if any)
     */
    public final void setNumTestsPerEvictionRun(final int numTestsPerEvictionRun) {

        Validate.inclusiveBetween(1, Integer.MAX_VALUE, numTestsPerEvictionRun);

        // no need for defensive copies of long

        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }


    /**
     * @return the minimum amount of time an object may sit idle in the pool before it is eligable for eviction by the
     * idle object evictor (if any)
     * @see ConnectionProperties#setMinEvictableIdleTimeMillis(long)
     */
    public final long getMinEvictableIdleTimeMillis() {

        // no need for defensive copies of long

        return minEvictableIdleTimeMillis;
    }

    /**
     * The minimum amount of time an object may sit idle in the pool before it is eligable for eviction by the idle
     * object evictor (if any).
     *
     * @param minEvictableIdleTimeMillis minimum amount of time an object may sit idle in the pool before it is
     *                                   eligable for eviction by the idle object evictor (if any).
     */
    public final void setMinEvictableIdleTimeMillis(final long minEvictableIdleTimeMillis) {

        // no need for validation, as long cannot be null and all possible values are allowed

        // no need for defensive copies of long

        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    /**
     * @return the minimum amount of time a connection may sit idle in the pool before it is eligible for eviction by
     * the idle connection evictor, with the extra condition that at least "minIdle" connections remain in the pool.
     * @see ConnectionProperties#setSoftMinEvictableIdleTimeMillis(long)
     */
    public final long getSoftMinEvictableIdleTimeMillis() {

        // no need for defensive copies of long

        return softMinEvictableIdleTimeMillis;
    }

    /**
     * The minimum amount of time a connection may sit idle in the pool before it is eligible for eviction by the idle
     * connection evictor, with the extra condition that at least "<code>minIdle</code>" connections remain in the pool.
     * When <code>miniEvictableIdleTimeMillis</code> is set to a positive value,
     * <code>miniEvictableIdleTimeMillis</code> is examined first by the idle connection evictor - i.e. when idle
     * connections are visited by the evictor, idle time is first compared against
     * <code>miniEvictableIdleTimeMillis</code> (without considering the number of idle connections in the pool) and
     * then against <code>softMinEvictableIdleTimeMillis</code>, including the <code>minIdle</code> constraint.
     *
     * @param softMinEvictableIdleTimeMillis minimum amount of time a connection may sit idle in the pool before it is
     *                                       eligible for eviction by the idle connection evictor, with the extra
     *                                       condition that at least "minIdle" connections remain in the pool.
     */
    public final void setSoftMinEvictableIdleTimeMillis(final long softMinEvictableIdleTimeMillis) {

        // no need for validation, as long cannot be null and all possible values are allowed

        // no need for defensive copies of long

        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

    /**
     * @return <code>true</code> if the pool returns the most recently used ("last in") connection, <code>false</code>
     * the pool behaves as a FIFO queue
     * @see ConnectionProperties#setLifo(boolean)
     */
    public final boolean isLifo() {
        return lifo;
    }

    /**
     * <code>True</code> means that the pool returns the most recently used ("last in") connection in the pool (if
     * there are idle connections available). <code>False</code> means that the pool behaves as a FIFO queue -
     * connections are taken from the idle instance pool in the order that they are returned to the pool.
     *
     * @param lifo <code>true</code> if the pool returns the most recently used ("last in") connection,
     *             <code>false</code> the pool behaves as a FIFO queue
     */
    public final void setLifo(final boolean lifo) {

        // no need for validation, as boolean cannot be null and all possible values are allowed

        // no need for defensive copies of boolean

        this.lifo = lifo;
    }

    /**
     * @return The default auto-commit state of connections created by the pool
     * @see ConnectionProperties#setDefaultAutoCommit(boolean)
     */
    public final boolean isDefaultAutoCommit() {

        // no need for defensive copies of boolean

        return defaultAutoCommit;
    }

    /**
     * The default auto-commit state of connections created by the pool.
     *
     * @param defaultAutoCommit The default auto-commit state of connections created by the pool
     */
    public final void setDefaultAutoCommit(final boolean defaultAutoCommit) {

        // no need for validation, as boolean cannot be null and all possible values are allowed

        // no need for defensive copies of boolean

        this.defaultAutoCommit = defaultAutoCommit;
    }

    /**
     * @return The default read-only state of connections created by the pool
     * @see ConnectionProperties#setDefaultReadOnly(boolean)
     */
    public final boolean isDefaultReadOnly() {

        // no need for defensive copies of boolean

        return defaultReadOnly;
    }

    /**
     * The default read-only state of connections created by the pool.
     * <p>
     * Note that some drivers do not support read only mode.
     *
     * @param defaultReadOnly The default read-only state of connections created by the pool
     */
    public final void setDefaultReadOnly(final boolean defaultReadOnly) {

        // no need for validation, as boolean cannot be null and all possible values are allowed

        // no need for defensive copies of boolean

        this.defaultReadOnly = defaultReadOnly;
    }

    /**
     * @return The default TransactionIsolation state of connections created by this pool
     * @see ConnectionProperties#setDefaultTransactionIsolation(int)
     */
    public final int getDefaultTransactionIsolation() {

        // no need for defensive copies of int

        return defaultTransactionIsolation;
    }

    /**
     * The default TransactionIsolation state of connections created by this pool.
     * <p>
     * The following values are allowed:
     * <ul>
     * <li>Connection.TRANSACTION_NONE</li>
     * <li>Connection.TRANSACTION_READ_COMMITTED</li>
     * <li>Connection.TRANSACTION_READ_UNCOMMITTED</li>
     * <li>Connection.TRANSACTION_REPEATABLE_READ</li>
     * <li>Connection.TRANSACTION_SERIALIZABLE</li>
     * </ul>
     *
     * @param defaultTransactionIsolation The default TransactionIsolation state of connections created by this pool
     * @see java.sql.Connection
     */
    public final void setDefaultTransactionIsolation(final int defaultTransactionIsolation) {

        if (defaultTransactionIsolation != Connection.TRANSACTION_NONE
                && defaultTransactionIsolation != Connection.TRANSACTION_READ_COMMITTED
                && defaultTransactionIsolation != Connection.TRANSACTION_READ_UNCOMMITTED
                && defaultTransactionIsolation != Connection.TRANSACTION_REPEATABLE_READ
                && defaultTransactionIsolation != Connection.TRANSACTION_SERIALIZABLE
                ) {
            throw new IllegalArgumentException("TransactionIsolation level must be one of the JDBC supported levels");
        }

        // no need for defensive copies of int

        this.defaultTransactionIsolation = defaultTransactionIsolation;
    }

    /**
     * @return If <code>true</code>, the pooled connection will cache the current <code>readOnly</code> and
     * <code>autoCommit</code> settings when first read or written and on all subsequent writes
     * @see ConnectionProperties#setCacheState(boolean)
     */
    public final boolean isCacheState() {

        // no need for defensive copies of boolean

        return cacheState;
    }

    /**
     * If <code>true</code>, the pooled connection will cache the current <code>readOnly</code> and
     * <code>autoCommit</code> settings when first read or written and on all subsequent writes. This removes the need
     * for additional database queries for any further calls to the getter. If the underlying connection is accessed
     * directly and the readOnly and/or <code>autoCommit</code> settings changed the cached values will not reflect the
     * current state. In this case, caching should be disabled by setting this attribute to false.
     *
     * @param cacheState If <code>true</code>, the pooled connection will cache the current <code>readOnly</code> and
     *                   <code>autoCommit</code> settings when first read or written and on all subsequent writes
     */
    public final void setCacheState(final boolean cacheState) {

        // no need for validation, as boolean cannot be null and all possible values are allowed

        // no need for defensive copies of boolean

        this.cacheState = cacheState;
    }

    /**
     * @return The SQL query that will be used to validate connections from the pool before returning them to the caller
     * @see ConnectionProperties#setValidationQuery(String)
     */
    public final String getValidationQuery() {

        // no need for defensive copies of String

        return validationQuery;
    }

    /**
     * The SQL query that will be used to validate connections from the pool before returning them to the caller. If
     * specified, this query <strong>MUST</strong> be an SQL SELECT statement that returns at least one row. If not
     * specified (i.e. <code>null</code>), connections will be validation by calling the <code>isValid()</code> method.
     *
     * @param validationQuery The SQL query that will be used to validate connections from the pool before returning
     *                        them to the caller
     */
    public final void setValidationQuery(final String validationQuery) {

        // no need for validation, as we cannot possible validate all SQL dialects and null is allowed for this string

        // no need for defensive copies of boolean

        this.validationQuery = validationQuery;
    }

    /**
     * @return The maximum lifetime in milliseconds of a connection
     * @see ConnectionProperties#setMaxConnLifetimeMillis(long)
     */
    public final long getMaxConnLifetimeMillis() {

        // no need for defensive copies of long

        return maxConnLifetimeMillis;
    }

    /**
     * The maximum lifetime in milliseconds of a connection. After this time is exceeded the connection will fail the
     * next activation, passivation or validation test. A value of zero or less means the connection has an infinite
     * lifetime.
     *
     * @param maxConnLifetimeMillis The maximum lifetime in milliseconds of a connection
     */
    public final void setMaxConnLifetimeMillis(final long maxConnLifetimeMillis) {

        // no need for validation, as long cannot be null and all possible values are allowed

        // no need for defensive copies of long

        this.maxConnLifetimeMillis = maxConnLifetimeMillis;
    }

    /**
     * @return Any additional properties stored in this object that have not explicitly been parsed
     * @see ConnectionProperties#setAdditionalProperties(Map<String, String>)
     */
    public final Map<String, String> getAdditionalProperties() {

        // create a defensive copy of the map and all its properties
        if (this.additionalProperties == null) {
            // this should never happen!
            return new ConcurrentHashMap<>();
        } else {
            final Map<String, String> tempMap = new ConcurrentHashMap<>();
            tempMap.putAll(additionalProperties);

            return tempMap;
        }
    }

    /**
     * Any additional properties which have not been parsed, and for which no getter/setter exists, but are to be
     * stored in this object nevertheless.
     * <p>
     * This property is commonly used to preserve original properties from upstream components that are to be passed
     * on to downstream components unchanged. This properties set may or may not include properties that have been
     * extracted from the map, and been made available through this POJO.
     * <p>
     * Note that these additional properties may be <code>null</code> or empty, even in a fully populated POJO where
     * other properties commonly have values assigned to.
     *
     * @param additionalProperties The additional properties to store
     */
    public final void setAdditionalProperties(final Map<String, String> additionalProperties) {

        // create a defensive copy of the map and all its properties
        if (additionalProperties == null) {
            this.additionalProperties = new ConcurrentHashMap<>();
        } else {
            this.additionalProperties = new ConcurrentHashMap<>();
            this.additionalProperties.putAll(additionalProperties);
        }
    }
}
