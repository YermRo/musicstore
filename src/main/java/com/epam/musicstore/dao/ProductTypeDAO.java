package com.epam.musicstore.dao;

import com.epam.musicstore.entity.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductTypeDAO extends AbstractDAO<ProductType> {
    private static final Logger LOG = LoggerFactory.getLogger(ProductTypeDAO.class);
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String PRODUCT_TYPE = "product_type";
    private static final String INSERT_PRODUCT_TYPE = "INSERT INTO musicstore.producttype(name_ru, " +
            "name_en) values(?, ?)";
    private static final String UPDATE_PRODUCT_TYPE_BY_ID = "UPDATE musicstore.producttype SET name_ru = ?, " +
            "name_en = ? WHERE id = ?";
    private static final String COULD_T_GET_PRODUCT_TYPE = "Could't get product type";
    private static final String COULDN_T_SET_PRODUCT_TYPE = "Couldn't set product type";

    @Override
    protected String getTableName() {
        return PRODUCT_TYPE;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_PRODUCT_TYPE;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_PRODUCT_TYPE_BY_ID;
    }

    @Override
    protected ProductType getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        ProductType productType = new ProductType();
        try{
            productType.setId(resultSet.getInt(ID));
            productType.setDeleted(resultSet.getBoolean(DELETED));
            productType.setEnName(resultSet.getString("name_en"));
            productType.setRuName(resultSet.getString("name_ru"));
        }
        catch (SQLException e){
            LOG.error(COULD_T_GET_PRODUCT_TYPE, e);
            throw new DAOException(COULD_T_GET_PRODUCT_TYPE, e);
        }
        return productType;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(ProductType productType,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setString(1,productType.getRuName());
            preparedStatement.setString(2,productType.getEnName());
        }
        catch (SQLException e){
            LOG.error(COULDN_T_SET_PRODUCT_TYPE, e);
            throw new DAOException(COULDN_T_SET_PRODUCT_TYPE, e);
        }
    }
}
