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

/**
 * This class specifies connection pool properties.
 */
// CHECKSTYLE:OFF
// this is flagged in checkstyle with a missing whitespace before '}', which is a bug in checkstyle
// suppress warnings field inits (better to have some explicit clarity what the field defaults in this class are)
// suppress warnings about the constructor (required for producing java docs)
// suppress warnings about the number of fields
// suppress waranings about the long variable names that are "inherited" from Apache DBCP (which I used as a blueprint)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.UnnecessaryConstructor", "PMD.TooManyFields", "PMD.LongVariable"})
// CHECKSTYLE:ON


public class ConnectionPoolSpec {

    /**
     * @see ConnectionPoolSpec#setMaxTotal(int)
     */
    private int maxTotal = 8;

    /**
     * @see ConnectionPoolSpec#setMaxIdle(int)
     */
    private int maxIdle = 8;

    /**
     * @see ConnectionPoolSpec#setMinIdle(int)
     */
    private int minIdle = 0;

    /**
     * @see ConnectionPoolSpec#setMaxWaitMillis(long)
     */
    private long maxWaitMillis = -1;

    /**
     * @see ConnectionPoolSpec#setTestOnCreate(boolean)
     */
    private boolean testOnCreate = false;

    /**
     * @see ConnectionPoolSpec#setTestOnBorrow(boolean)
     */
    private boolean testOnBorrow = true;

    /**
     * @see ConnectionPoolSpec#setTestOnReturn(boolean)
     */
    private boolean testOnReturn = false;

    /**
     * @see ConnectionPoolSpec#setTestWhileIdle(boolean)
     */
    private boolean testWhileIdle = false;

    /**
     * @see ConnectionPoolSpec#setTimeBetweenEvictionRunsMillis(long)
     */
    private long timeBetweenEvictionRunsMillis = -1;

    /**
     * @see ConnectionPoolSpec#setNumTestsPerEvictionRun(int)
     */
    private int numTestsPerEvictionRun = 3;

    /**
     * @see ConnectionPoolSpec#setMinEvictableIdleTimeMillis(long)
     */
    private long minEvictableIdleTimeMillis = 1000 * 60 * 30;

    /**
     * @see ConnectionPoolSpec#setSoftMinEvictableIdleTimeMillis(long)
     */
    private long softMinEvictableIdleTimeMillis = -1;

    /**
     * @see ConnectionPoolSpec#setLifo(boolean)
     */
    private boolean lifo = true;

    /**
     * @see ConnectionPoolSpec#setDefaultAutoCommit(boolean)
     */
    private boolean defaultAutoCommit = true;

    /**
     * @see ConnectionPoolSpec#setDefaultReadOnly(boolean)
     */
    private boolean defaultReadOnly = false;

    /**
     * @see ConnectionPoolSpec#setDefaultTransactionIsolation(int)
     */
    private int defaultTransactionIsolation = Connection.TRANSACTION_REPEATABLE_READ;

    /**
     * @see ConnectionPoolSpec#setCacheState(boolean)
     */
    private boolean cacheState = true;

    /**
     * @see ConnectionPoolSpec#setValidationQuery(String)
     */
    private String validationQuery = "SELECT 1";

    /**
     * @see ConnectionPoolSpec#setMaxConnLifetimeMillis(long)
     */
    private long maxConnLifetimeMillis = -1;

    /**
     * Constructs a default connection pool spec, see the setters for the default values being used.
     * <p/>
     * You can change the defaults with the setters. If you need more control over the pool than what is provided by
     * the available setters, consider using a JNDI controlled connection pool instead.
     */
    public ConnectionPoolSpec() {

        // no code here, constructor just for java docs
    }

    /**
     * @return the maximum numbers of active connections
     * @see ConnectionPoolSpec#setMaxTotal(int)
     */
    public final int getMaxTotal() {

        // no need for defensive copies of int

        return maxTotal;
    }

    /**
     * The maximum number of active connections that can be allocated from this pool at the same time, or negative
     * for no limit.
     * <p/>
     * Default: 8
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
     * @see ConnectionPoolSpec#setMaxIdle(int)
     */
    public final int getMaxIdle() {

        // no need for defensive copies of int

        return maxIdle;
    }

    /**
     * The maximum number of connections that can remain idle in the pool, without extra ones being released, or
     * negative for no limit.
     * <p/>
     * Default: 8
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
     * @see ConnectionPoolSpec#setMinIdle(int)
     */
    public final int getMinIdle() {

        // no need for defensive copies of int

        return minIdle;
    }

    /**
     * The minimum number of connections that can remain idle in the pool, without extra ones being created, or zero
     * to create none.
     * <p/>
     * Default: 0
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
     * @see ConnectionPoolSpec#setMaxWaitMillis(long)
     */
    public final long getMaxWaitMillis() {

        // no need for defensive copies of long

        return maxWaitMillis;
    }

    /**
     * The maximum number of milliseconds that the pool will wait (when there are no available connections) for a
     * connection to be returned before throwing an exception, or -1 to wait indefinitely.
     * <p/>
     * Default: -1
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
     * @see ConnectionPoolSpec#setTestOnCreate(boolean)
     */
    public final boolean isTestOnCreate() {

        // no need for defensive copies of boolean

        return testOnCreate;
    }

    /**
     * The indication of whether objects will be validated after creation. If the object fails to validate, the borrow
     * attempt that triggered the object creation will fail.
     * <p/>
     * Default: false
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
     * @see ConnectionPoolSpec#setTestOnBorrow(boolean)
     */
    public final boolean isTestOnBorrow() {

        // no need for defensive copies of boolean

        return testOnBorrow;
    }

    /**
     * The indication of whether objects will be validated before being borrowed from the pool. If the object fails to
     * validate, it will be dropped from the pool, and we will attempt to borrow another.
     * <p/>
     * Default: true
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
     * @see ConnectionPoolSpec#setTestOnReturn(boolean)
     */
    public final boolean isTestOnReturn() {

        // no need for defensive copies of boolean

        return testOnReturn;
    }

    /**
     * The indication of whether objects will be validated before being returned to the pool.
     * <p/>
     * Default: false
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
     * @see ConnectionPoolSpec#setTestWhileIdle(boolean)
     */
    public final boolean isTestWhileIdle() {

        // no need for defensive copies of boolean

        return testWhileIdle;
    }

    /**
     * The indication of whether objects will be validated by the idle object evictor (if any). If an object fails
     * to validate, it will be dropped from the pool.
     * <p/>
     * Default: false
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
     * @see ConnectionPoolSpec#setTimeBetweenEvictionRunsMillis(long)
     */
    public final long getTimeBetweenEvictionRunsMillis() {

        // no need for defensive copies of long

        return timeBetweenEvictionRunsMillis;
    }

    /**
     * The number of milliseconds to sleep between runs of the idle object evictor thread. When non-positive, no idle
     * object evictor thread will be run.
     * <p/>
     * Default: -1
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
     * @see ConnectionPoolSpec#setNumTestsPerEvictionRun(int)
     */
    public final int getNumTestsPerEvictionRun() {

        // no need for defensive copies of int

        return numTestsPerEvictionRun;
    }

    /**
     * The number of objects to examine during each run of the idle object evictor thread (if any).
     * <p/>
     * Default: 3
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
     * @see ConnectionPoolSpec#setMinEvictableIdleTimeMillis(long)
     */
    public final long getMinEvictableIdleTimeMillis() {

        // no need for defensive copies of long

        return minEvictableIdleTimeMillis;
    }

    /**
     * The minimum amount of time an object may sit idle in the pool before it is eligable for eviction by the idle
     * object evictor (if any).
     * <p/>
     * Default: 1000 * 60 * 30
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
     * @see ConnectionPoolSpec#setSoftMinEvictableIdleTimeMillis(long)
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
     * <p/>
     * Default: -1
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
     * @see ConnectionPoolSpec#setLifo(boolean)
     */
    public final boolean isLifo() {
        return lifo;
    }

    /**
     * <code>True</code> means that the pool returns the most recently used ("last in") connection in the pool (if
     * there are idle connections available). <code>False</code> means that the pool behaves as a FIFO queue -
     * connections are taken from the idle instance pool in the order that they are returned to the pool.
     * <p/>
     * Default: true
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
     * @see ConnectionPoolSpec#setDefaultAutoCommit(boolean)
     */
    public final boolean isDefaultAutoCommit() {

        // no need for defensive copies of boolean

        return defaultAutoCommit;
    }

    /**
     * The default auto-commit state of connections created by the pool.
     * <p/>
     * Default: true
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
     * @see ConnectionPoolSpec#setDefaultReadOnly(boolean)
     */
    public final boolean isDefaultReadOnly() {

        // no need for defensive copies of boolean

        return defaultReadOnly;
    }

    /**
     * The default read-only state of connections created by the pool.
     * <p/>
     * Note that some drivers do not support read only mode.
     * <p/>
     * Default: false
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
     * @see ConnectionPoolSpec#setDefaultTransactionIsolation(int)
     */
    public final int getDefaultTransactionIsolation() {

        // no need for defensive copies of int

        return defaultTransactionIsolation;
    }

    /**
     * The default TransactionIsolation state of connections created by this pool.
     * <p/>
     * The following values are allowed:
     * <ul>
     * <li>Connection.TRANSACTION_NONE</li>
     * <li>Connection.TRANSACTION_READ_COMMITTED</li>
     * <li>Connection.TRANSACTION_READ_UNCOMMITTED</li>
     * <li>Connection.TRANSACTION_REPEATABLE_READ</li>
     * <li>Connection.TRANSACTION_SERIALIZABLE</li>
     * </ul>
     * Default: Connection.TRANSACTION_REPEATABLE_READ
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
     * @see ConnectionPoolSpec#setCacheState(boolean)
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
     * <p/>
     * Default: true
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
     * @see ConnectionPoolSpec#setValidationQuery(String)
     */
    public final String getValidationQuery() {

        // no need for defensive copies of String

        return validationQuery;
    }

    /**
     * The SQL query that will be used to validate connections from the pool before returning them to the caller. If
     * specified, this query <strong>MUST</strong> be an SQL SELECT statement that returns at least one row. If not
     * specified (i.e. <code>null</code>), connections will be validation by calling the <code>isValid()</code> method.
     * <p/>
     * Default: "SELECT 1"
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
     * @see ConnectionPoolSpec#setMaxConnLifetimeMillis(long)
     */
    public final long getMaxConnLifetimeMillis() {

        // no need for defensive copies of long

        return maxConnLifetimeMillis;
    }

    /**
     * The maximum lifetime in milliseconds of a connection. After this time is exceeded the connection will fail the
     * next activation, passivation or validation test. A value of zero or less means the connection has an infinite
     * lifetime.
     * <p/>
     * Default: -1
     *
     * @param maxConnLifetimeMillis The maximum lifetime in milliseconds of a connection
     */
    public final void setMaxConnLifetimeMillis(final long maxConnLifetimeMillis) {

        // no need for validation, as long cannot be null and all possible values are allowed

        // no need for defensive copies of long

        this.maxConnLifetimeMillis = maxConnLifetimeMillis;
    }
}
