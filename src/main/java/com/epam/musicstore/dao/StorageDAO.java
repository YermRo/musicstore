package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageDAO extends AbstractDAO<Storage> {
    private static final Logger LOG = LoggerFactory.getLogger(StorageDAO.class);
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String DESCRIPTION_RU = "description_ru";
    private static final String DESCRIPTION_EN = "description_en";
    private static final String STORAGE = "storage";
    private static final String INSERT_STORAGE = "INSERT INTO musicstore.storage(name,description_ru, " +
            "description_en) values(?, ?, ?)";
    private static final String UPDATE_STORAGE_BY_ID = "UPDATE musicstore.storage SET name = ?, " +
            "description_ru = ?, description_en = ? WHERE id = ?";
    private static final String COULDN_T_GET_STORAGE = "Couldn't get storage";
    private static final String COULDN_T_SET_STORAGE = "Couldn't set storage";

    @Override
    protected String getTableName() {
        return STORAGE;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_STORAGE;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_STORAGE_BY_ID;
    }

    @Override
    protected Storage getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Storage storage = new Storage();
        try{
            storage.setId(resultSet.getInt(ID));
            storage.setDeleted(resultSet.getBoolean(DELETED));
            storage.setRuDescription(resultSet.getString(DESCRIPTION_RU));
            storage.setEnDescription(resultSet.getString(DESCRIPTION_EN));
        }
        catch (SQLException e){
            LOG.error(COULDN_T_GET_STORAGE, e);
            throw new DAOException(COULDN_T_GET_STORAGE, e);
        }
        return storage;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Storage storage,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try{
            preparedStatement.setString(1, storage.getName());
            preparedStatement.setString(2,storage.getRuDescription());
            preparedStatement.setString(3,storage.getEnDescription());
        }
        catch (SQLException e){
            LOG.error(COULDN_T_SET_STORAGE, e);
            throw new DAOException(COULDN_T_SET_STORAGE, e);
        }
    }
}
