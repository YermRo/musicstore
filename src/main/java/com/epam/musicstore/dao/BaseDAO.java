package com.epam.musicstore.dao;



import com.epam.musicstore.entity.BaseEntity;

import java.util.List;
import java.util.Map;

public interface BaseDAO<T extends BaseEntity> {
    T getByID(Integer id) throws DAOException;

    List<T> getAll() throws DAOException;

    List<T> getAll(int pageNumber, int pageSize) throws DAOException;

    T insert(T entity) throws DAOException;

     void update (T entity) throws DAOException;

    void deleteByID (Integer id) throws DAOException;

    void deleteStorageItemByProductID (Integer id) throws DAOException;

    List<T> getByParams (Map<String, String> params) throws DAOException;

    int getNotDeletedCount() throws DAOException;
}
