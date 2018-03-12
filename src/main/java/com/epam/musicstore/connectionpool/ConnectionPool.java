package com.epam.musicstore.connectionpool;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);
    private static final String LOAD_PROP_FILE_WITH_INFO_ABOUT_DB = "Load prop file with info about DB";
    private static final String COULD_NOT_LOAD_DB_PROPERTIES = "could not load DB properties";
    private static final String SET_INFORMATION_ABOUT_DB = "Set information about DB";
    private static final String URL = "url";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";
    public static final String CONNECTIONS_LIMIT = "connectionsLimit";
    private static final String CONNECTIONS = "connections";
    private static final String COULDN_T_GET_PARAMETERS_FROM_PROPERTY = "Couldn't get parameters from property";
    private static final String REGISTERING_DRIVER = "Registering driver";
    private static final String COULDN_T_REGISTER_DRIVER = "Couldn't RegisterUser driver";
    private static final String ERROR_IN_INITIALIZE_POOL = "Error in initializePool()";
    private static final String USED_CONNECTIONS = "used connections: ";
    private static final String FREE_CONNECTIONS = "free connections: ";
    private static final String COULDN_T_GET_CONNECTION = "Couldn't get connection";
    private static final String COULDN_T_CLOSE_CONNECTION = "Couldn't close connection";
    private static final String COULDN_T_CREATE_CONNECTION_POOL = "Couldn't create connection pool";
    public static final String DATABASE_DATABASE_PROPERTIES = "database/database.properties";

    private String url;
    private String username;
    private String password;
    private String driver;
    private int connectionsLimit;

    private BlockingQueue<Connection> freeConnections = null;
    private BlockingQueue<Connection> usedConnections = null;

    public ConnectionPool(){
        try {
            loadDBProperties();
            registerDriver();
            initializePool();
        }
        catch (ConnectionPoolException e){
            LOG.error(COULDN_T_CREATE_CONNECTION_POOL, e);
        }
    }

    private void loadDBProperties() throws ConnectionPoolException {
        Properties properties = new Properties();

        try {
            InputStream is = this.getClass().getClassLoader()
                    .getResourceAsStream("database.properties");
           properties.load(is);
            LOG.debug(LOAD_PROP_FILE_WITH_INFO_ABOUT_DB);
        } catch (IOException e) {
            LOG.error(COULD_NOT_LOAD_DB_PROPERTIES, e);
            throw new ConnectionPoolException(COULD_NOT_LOAD_DB_PROPERTIES, e);
        }
        if (!properties.isEmpty()) {
            LOG.debug(SET_INFORMATION_ABOUT_DB);
            setUrl(properties.getProperty(URL));
            setUsername(properties.getProperty(USERNAME));
            setPassword(properties.getProperty(PASSWORD));
            setDriver(properties.getProperty(DRIVER));
            setConnectionsLimit(Integer.parseInt(properties.getProperty(CONNECTIONS)));
        } else {
            LOG.error(COULDN_T_GET_PARAMETERS_FROM_PROPERTY);
        }
    }

    private void registerDriver() throws ConnectionPoolException {
        try {
            LOG.info(REGISTERING_DRIVER);
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            LOG.error(COULDN_T_REGISTER_DRIVER, e);
            throw new ConnectionPoolException(COULDN_T_REGISTER_DRIVER, e);
        }
    }

    private void initializePool() throws ConnectionPoolException {
        if (freeConnections == null) {
            freeConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        if (usedConnections == null) {
            usedConnections = new ArrayBlockingQueue<>(connectionsLimit);
        }
        try {
            Class.forName(driver);
            for (int i = 0; i < connectionsLimit; i++) {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.put(connection);
            }
        } catch (InterruptedException | ClassNotFoundException | SQLException e) {
            LOG.error(ERROR_IN_INITIALIZE_POOL, e);
            throw new ConnectionPoolException(ERROR_IN_INITIALIZE_POOL, e);
        }
    }

    public synchronized Connection getConnection() throws ConnectionPoolException {
        Connection currentConnection;
        LOG.info(FREE_CONNECTIONS + freeConnections.size() + USED_CONNECTIONS + usedConnections.size());
        try {
            currentConnection = freeConnections.take();
            usedConnections.add(currentConnection);
        } catch (InterruptedException e) {
            LOG.error(COULDN_T_GET_CONNECTION, e);
            throw new ConnectionPoolException(COULDN_T_GET_CONNECTION, e);
        }
        LOG.info(FREE_CONNECTIONS + freeConnections.size() + USED_CONNECTIONS + usedConnections.size());
        return currentConnection;
    }

    public synchronized void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            LOG.info(FREE_CONNECTIONS + freeConnections.size() + USED_CONNECTIONS + usedConnections.size());
            usedConnections.remove(connection);
            freeConnections.put(connection);
            LOG.info(FREE_CONNECTIONS + freeConnections.size() + USED_CONNECTIONS + usedConnections.size());
        } catch (InterruptedException e) {
            LOG.error(COULDN_T_CLOSE_CONNECTION, e);
            throw new ConnectionPoolException(COULDN_T_GET_CONNECTION, e);
        }
    }

    public void closeAllConnections(BlockingQueue<Connection> connections) throws ConnectionPoolException {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.error(COULDN_T_CLOSE_CONNECTION, e);
                throw new ConnectionPoolException(COULDN_T_CLOSE_CONNECTION, e);
            }
        }
    }

    public void close() throws ConnectionPoolException {
        closeAllConnections(freeConnections);
        closeAllConnections(usedConnections);

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }

    public static class InstanceHolder{
        static  ConnectionPool instance;
        public static void setInstance(ConnectionPool connectionPool) {
            instance = connectionPool;
        }
    }

    public static synchronized ConnectionPool getInstance() {
        return InstanceHolder.instance;
    }


}
