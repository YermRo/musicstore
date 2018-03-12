package com.epam.musicstore.dao;

import com.epam.musicstore.connectionpool.ConnectionPool;
import com.epam.musicstore.connectionpool.ConnectionPoolException;
import com.epam.musicstore.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.SQLException;

public class DBDAOFactory extends DAOFactory {
    private static final Logger LOG = LoggerFactory.getLogger(DBDAOFactory.class);

    private static final String FORMAT = "%s.%sDAO";
    private static final String UNABLE_TO_GET_CONNECTION_FROM_THE_POOL = "Unable to get connection from the pool";
    private static final String CANNOT_BEGIN_TRANSACTION = "Cannot begin transaction";
    private static final String COMMIT_TRANSACTION = "Commit transaction";
    private static final String CANNOT_COMMIT_TRANSACTION = "Cannot commit transaction";
    private static final String ROLLBACK_TRANSACTION = "Rollback transaction";
    private static final String CANNOT_ROLLBACK_TRANSACTION = "Cannot rollback transaction";
    private static final String CANNOT_CLOSE_CONNECTION = "Cannot close connection";

    private Connection connection;
    ConnectionPool connectionPool;

    public DBDAOFactory() {
        connectionPool = ConnectionPool.getInstance();
        try {
            connection = connectionPool.getConnection();
        } catch (ConnectionPoolException e) {
            LOG.error(UNABLE_TO_GET_CONNECTION_FROM_THE_POOL, e);
        }
    }

    @Override
    public <T extends BaseEntity> BaseDAO<T> getDAO(Class<T> tClass) {
        AbstractDAO<T> daoObject = null;
        try{
            String inputClassName = tClass.getSimpleName();
            String packageName = this.getClass().getPackage().getName();
            String resultClassName = String.format(FORMAT, packageName, inputClassName);
            daoObject = (AbstractDAO<T>) Class.forName(resultClassName).newInstance();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){

        }
        daoObject.setConnection(connection);
        return daoObject;
    }

    @Override
    public void close() throws DAOException {
        try {
            connectionPool.closeConnection(connection);
        } catch (ConnectionPoolException e) {
            LOG.info(CANNOT_CLOSE_CONNECTION, e);
            throw new DAOException(CANNOT_CLOSE_CONNECTION, e);
        }
    }



    @Override
    public void startTransaction() {
        try {

            connection.setAutoCommit(false);
        } catch (SQLException e) {

        }
    }

    @Override
    public void commitTransaction() {
        try {
         connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {

        }
    }


    @Override
    public void rollbackTransaction() {
        try {
            connection.rollback();
        }
        catch (SQLException e){

        }
    }
}
