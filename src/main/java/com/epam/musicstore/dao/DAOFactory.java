package com.epam.musicstore.dao;


import com.epam.musicstore.entity.BaseEntity;

public abstract class DAOFactory implements AutoCloseable {
    public static DAOFactory getDAOFactory() {
        return new DBDAOFactory();
    }

    public abstract <T extends BaseEntity> BaseDAO<T> getDAO(Class<T> tClass);

    public abstract void close() throws DAOException;

    public abstract void startTransaction();

    public abstract void commitTransaction();

    public abstract void rollbackTransaction();
}
