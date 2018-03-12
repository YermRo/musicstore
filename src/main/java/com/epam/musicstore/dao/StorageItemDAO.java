package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Product;
import com.epam.musicstore.entity.Storage;
import com.epam.musicstore.entity.StorageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StorageItemDAO extends AbstractDAO<StorageItem> {
    public static final String SET_DELETED_1 = " SET deleted = 1";
    private static final Logger LOG = LoggerFactory.getLogger(StorageItemDAO.class);
    private static final String PRODUCT_ID = "product_id";
    private static final String STORAGE_ID = "storage_id";
    private static final String AMOUNT = "amount";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String STORAGE_ITEM = "storage_item";
    private static final String INSERT_STORAGE_ITEM = "INSERT INTO musicstore.storage_item(product_id, storage_id, " +
            "amount) VALUES(?, ?, ?)";
    private static final String UPDATE_STORAGE_ITEM_BY_ID = "UPDATE musicstore.storage_item SET product_id = ?," +
            " storage_id = ?, amount = ? WHERE id = ?";
    private static final String COULDN_T_GET_STORAGE_ITEM = "Couldn't get storage item";
    private static final String COULDN_T_SET_STORAGE_ITEM = "Couldn't set storage item";
    private static final String WHERE_PRODUCT_ID = "WHERE product_id = ";
    private static final String STORAGE_ITEM_DELETED_BY_PRODUCT_ID = "Storage item deleted by product id";
    private static final String COULDN_T_DELETE_STORAGE_ITEM = "Couldn't delete storage item";
    public static final String UPDATE_MUSICSTORE_STORAGE_ITEM = "UPDATE musicstore.storage_item ";

    @Override
    protected String getTableName() {
        return STORAGE_ITEM;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_STORAGE_ITEM;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_STORAGE_ITEM_BY_ID;
    }

    @Override
    protected StorageItem getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        StorageItem storageItem = new StorageItem();
        try {
            Product product = new Product(resultSet.getInt(PRODUCT_ID));
            Storage storage = new Storage(resultSet.getInt(STORAGE_ID));
            storageItem.setProduct(product);
            storageItem.setStorage(storage);
            storageItem.setAmount(resultSet.getInt(AMOUNT));
            storageItem.setId(resultSet.getInt(ID));
            storageItem.setDeleted(resultSet.getBoolean(DELETED));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_STORAGE_ITEM, e);
            throw new DAOException(COULDN_T_GET_STORAGE_ITEM, e);
        }
        return storageItem;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(StorageItem storageItem,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setInt(1, storageItem.getProduct().getId());
            preparedStatement.setInt(2, storageItem.getStorage().getId());
            preparedStatement.setInt(3, storageItem.getAmount());
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_STORAGE_ITEM, e);
            throw new DAOException(COULDN_T_SET_STORAGE_ITEM, e);
        }
    }


}
