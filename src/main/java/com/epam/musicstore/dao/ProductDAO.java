package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Product;
import com.epam.musicstore.entity.ProductType;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDAO extends AbstractDAO<Product> {
    private static final Logger LOG = LoggerFactory.getLogger(ProductDAO.class);
    private static final String NAME = "name";
    private static final String KZT = "KZT";
    private static final String PRICE = "price";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String DESCRIPTION_RU = "description_ru";
    private static final String DESCRIPTION_EN = "description_en";
    private static final String PRODUCT = "product";
    private static final String INSERT_PRODUCT = "INSERT INTO musicstore.product(name, type_id, price, " +
            "description_RU, description_EN) VALUES(?, ?, ?, ?, ?)";

    private static final String UPDATE_PRODUCT_BY_ID = "UPDATE musicstore.product SET name = ?, type_id = ?, " +
            "price = ?, description_RU = ?, description_EN = ? WHERE id = ?";
    private static final String COULDN_T_GET_PRODUCT = "Couldn't get product";
    private static final String COULDN_T_SET_PRODUCT = "Couldn't set product";
    private static final String TYPE_ID = "type_id";

    @Override
    protected String getTableName() {
        return PRODUCT;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_PRODUCT;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_PRODUCT_BY_ID;
    }

    @Override
    protected Product getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Product product = null;
        try {
            Money price = Money.of(CurrencyUnit.getInstance(KZT), resultSet.getBigDecimal(PRICE));
            ProductType productType = new ProductType(resultSet.getInt(TYPE_ID));
            product = new Product.Builder()
                    .setName(resultSet.getString(NAME))
                    .setPrice(price)
                    .setType(productType)
                    .build();
            product.setId(resultSet.getInt(ID));
            product.setDeleted(resultSet.getBoolean(DELETED));
            product.setRuDescription(resultSet.getString(DESCRIPTION_RU));
            product.setEnDescription(resultSet.getString(DESCRIPTION_EN));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_PRODUCT, e);
            throw new DAOException(COULDN_T_GET_PRODUCT, e);
        }
        return product;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Product product,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getType().getId());
            preparedStatement.setBigDecimal(3, product.getPrice().getAmount());

            preparedStatement.setString(4, product.getRuDescription());
            preparedStatement.setString(5, product.getEnDescription());
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_PRODUCT, e);
            throw new DAOException(COULDN_T_SET_PRODUCT, e);
        }
    }
}
