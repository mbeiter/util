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

import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.lang3.Validate;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This class creates and manages JDBC Connection instances from:
 * <ul>
 * <li>A named JNDI managed connection</li>
 * <li>A connection pool that is maintained by this factory</li>
 * </ul>
 */
public final class ConnectionFactory {

    /**
     * The logger object for this class
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);


    /**
     * This hash map stores the generated pools per connection
     */
    private static final ConcurrentHashMap<String, PoolingDataSource<PoolableConnection>> CONNECTION_POOLS =
            new ConcurrentHashMap<>();

    /**
     * A private constructor to prevent instantiation of this class
     */
    private ConnectionFactory() {
    }

    /**
     * Return a Connection instance for a JNDI managed JDBC connection.
     *
     * @param jndiName The JNDI connection name
     * @return a JDBC connection
     * @throws FactoryException         When the connection cannot be retrieved from JNDI
     * @throws NullPointerException     When {@code jndiName} is null
     * @throws IllegalArgumentException When {@code jndiName} is empty
     */
    public static Connection getConnection(final String jndiName)
            throws FactoryException {

        Validate.notBlank(jndiName, "The validated character sequence 'jndiName' is null or empty");

        try {
            // the initial context is created from the provided JNDI settings
            final Context context = new InitialContext();

            // retrieve a data source object, close the context as it is no longer needed, and return the connection
            final Object namedObject = context.lookup(jndiName);
            if (DataSource.class.isInstance(namedObject)) {
                final DataSource dataSource = (DataSource) context.lookup(jndiName);
                context.close();

                return dataSource.getConnection();
            } else {
                final String error = "The JNDI name '" + jndiName + "' does not reference a SQL DataSource."
                        + " This is a configuration issue.";
                LOG.warn(error);
                throw new FactoryException(error);
            }
        } catch (SQLException | NamingException e) {
            final String error = "Error retrieving JDBC connection from JNDI: " + jndiName;
            LOG.warn(error);
            throw new FactoryException(error, e);
        }
    }

    /**
     * Return a Connection instance from a pool that manages JDBC driver based connections.
     * <p>
     * The driver-based connection are managed in a connection pool. The pool is created using the provided properties
     * for both the connection and the pool spec. Once the pool has been created, it is cached (based on URL and
     * username), and can no longer be changed. Subsequent calls to this method will return a connection from the
     * cached pool, and changes in the pool spec (e.g. changes to the size of the pool) will be ignored.
     *
     * @param poolSpec A connection pool spec that has the driver and url configured as non-empty strings
     * @return a JDBC connection
     * @throws FactoryException         When the connection cannot be retrieved from the pool, or the pool cannot be
     *                                  created
     * @throws NullPointerException     When the {@code poolSpec}, {@code poolSpec.getDriver()}, or
     *                                  {@code poolSpec.getUrl()} are {@code null}
     * @throws IllegalArgumentException When {@code poolSpec.getDriver()} or {@code poolSpec.getUrl()} are empty
     */
    public static Connection getConnection(final ConnectionProperties poolSpec)
            throws FactoryException {

        Validate.notNull(poolSpec, "The validated object 'poolSpec' is null");
        Validate.notBlank(poolSpec.getDriver(),
                "The validated character sequence 'poolSpec.getDriver()' is null or empty");
        Validate.notBlank(poolSpec.getUrl(), "The validated character sequence 'poolSpec.getUrl()' is null or empty");

        // no need for defensive copies of Strings

        final String driver = poolSpec.getDriver();
        final String url = poolSpec.getUrl();
        // CHECKSTYLE:OFF
        // this particular set of inline conditions is easy to read :-)
        final String username = poolSpec.getUsername() == null ? "" : poolSpec.getUsername();
        final String password = poolSpec.getPassword() == null ? "" : poolSpec.getPassword();
        // CHECKSTYLE:OFF

        // Load the database driver (if not already done)
        loadDriver(driver);

        // create the hash map required for the connection pool username + password
        final ConcurrentMap<String, String> properties = new ConcurrentHashMap<>();
        properties.put("user", username);
        properties.put("password", password);

        // we keep a separate pool per connection
        // a connection is identified by the URL, the username, and the password
        final String key = String.format("%s:%s", url, username);

        // avoid if possible to create the pool multiple times, and store the data source pool for later use
        if (!CONNECTION_POOLS.containsKey(key)) {
            synchronized (ConnectionFactory.class) {
                if (!CONNECTION_POOLS.containsKey(key)) {

                    // this call is thread safe even without the double if check and extra synchronization. However, it
                    // might happen that the pool is created multiple times. While additional copies would be simply
                    // thrown away, we might run into problems in case that, for instance, the number of connections
                    // from the same user / machine are restricted on the DB server.
                    // While this does not happen a lot (it only happens if there is not already an entry and multiple
                    // threads race this block and lose), it could still lead to a failure, and we must take this double
                    // sync workaround. There is a solution for Java 8 - see below.
                    CONNECTION_POOLS.putIfAbsent(key, getPoolingDataSource(url, properties, poolSpec));
                }
            }
        }
        // This would solve the problem of multiple pools being created and all but one being throws away, but it
        // does not work before Java 8 because the "computeIfAbsent()" method with the lambda function is not
        // available before Java 8:
        // TODO: add the pooled data source with the "computeIfAbsent()" method to improve performance in Java 8
        //CONNECTION_POOLS.computeIfAbsent(key, k -> getPoolingDataSource(url, properties, poolSpec));

        try {
            return CONNECTION_POOLS.get(key).getConnection();
        } catch (SQLException e) {
            final String error = "Error retrieving JDBC connection from pool: " + key;
            LOG.warn(error);
            throw new FactoryException(error, e);
        }
    }

    /**
     * Resets the internal state of the factory.
     * <p>
     * <strong>This method does not release any resources that have been borrowed from the connection pools managed
     * by this factory.</strong> To avoid resource leaks, you <strong>must</strong> close / return all connections to
     * their pools before calling this method.
     */
    public static void reset() {

        // Unset the cached connections
        CONNECTION_POOLS.clear();
    }

    /**
     * Make sure that the database driver exists
     *
     * @param driver The JDBC driver class to load
     * @throws FactoryException When the driver cannot be loaded
     */
    private static void loadDriver(final String driver) throws FactoryException {

        // assert in private method
        assert driver != null : "The driver cannot be null";

        LOG.debug("Loading the database driver '" + driver + "'");

        // make sure the driver is available
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            final String error = "Error loading JDBC driver class: " + driver;
            LOG.warn(error, e);
            throw new FactoryException(error, e);
        }
    }

    /**
     * Get a pooled data source for the provided connection parameters.
     *
     * @param url        The JDBC database URL of the form <code>jdbc:subprotocol:subname</code>
     * @param properties A list of key/value configuration parameters to pass as connection arguments. Normally at
     *                   least a "user" and "password" property should be included
     * @param poolSpec   A connection pool spec
     * @return A pooled database connection
     */
    private static PoolingDataSource<PoolableConnection> getPoolingDataSource(final String url,
                                                                              final ConcurrentMap<String, String> properties,
                                                                              final ConnectionProperties poolSpec) {

        // assert in private method
        assert url != null : "The url cannot be null";
        assert properties != null : "The properties cannot be null";
        assert poolSpec != null : "The pol spec cannot be null";

        LOG.debug("Creating new pooled data source for '" + url + "'");

        // convert the properties hashmap to java properties
        final Properties props = new Properties();
        props.putAll(properties);

        // create a Apache DBCP pool configuration from the pool spec
        final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(poolSpec.getMaxTotal());
        poolConfig.setMaxIdle(poolSpec.getMaxIdle());
        poolConfig.setMinIdle(poolSpec.getMinIdle());
        poolConfig.setMaxWaitMillis(poolSpec.getMaxWaitMillis());
        poolConfig.setTestOnCreate(poolSpec.isTestOnCreate());
        poolConfig.setTestOnBorrow(poolSpec.isTestOnBorrow());
        poolConfig.setTestOnReturn(poolSpec.isTestOnReturn());
        poolConfig.setTestWhileIdle(poolSpec.isTestWhileIdle());
        poolConfig.setTimeBetweenEvictionRunsMillis(poolSpec.getTimeBetweenEvictionRunsMillis());
        poolConfig.setNumTestsPerEvictionRun(poolSpec.getNumTestsPerEvictionRun());
        poolConfig.setMinEvictableIdleTimeMillis(poolSpec.getMinEvictableIdleTimeMillis());
        poolConfig.setSoftMinEvictableIdleTimeMillis(poolSpec.getSoftMinEvictableIdleTimeMillis());
        poolConfig.setLifo(poolSpec.isLifo());


        // create the pool and assign the factory to the pool
        final org.apache.commons.dbcp2.ConnectionFactory connFactory = new DriverManagerConnectionFactory(url, props);
        final PoolableConnectionFactory poolConnFactory = new PoolableConnectionFactory(connFactory, null);
        poolConnFactory.setDefaultAutoCommit(poolSpec.isDefaultAutoCommit());
        poolConnFactory.setDefaultReadOnly(poolSpec.isDefaultReadOnly());
        poolConnFactory.setDefaultTransactionIsolation(poolSpec.getDefaultTransactionIsolation());
        poolConnFactory.setCacheState(poolSpec.isCacheState());
        poolConnFactory.setValidationQuery(poolSpec.getValidationQuery());
        poolConnFactory.setMaxConnLifetimeMillis(poolSpec.getMaxConnLifetimeMillis());
        final GenericObjectPool<PoolableConnection> connPool = new GenericObjectPool<>(poolConnFactory, poolConfig);
        poolConnFactory.setPool(connPool);

        // create a new pooled data source
        return new PoolingDataSource<>(connPool);
    }
}
