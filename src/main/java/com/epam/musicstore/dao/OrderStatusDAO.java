package com.epam.musicstore.dao;

import com.epam.musicstore.entity.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDAO extends AbstractDAO<OrderStatus> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderStatus.class);
    private static final String NAME_RU = "name_ru";
    private static final String NAME_EN = "name_en";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String ORDER_STATUS = "order_status";
    private static final String INSERT_ORDER_STATUS = "INSERT INTO musicstore.orderstatus(name_ru, name_en) " +
            "VALUES(?, ?)";
    private static final String UPDATE_ORDER_STATUS_BY_ID = "UPDATE musicstore.orderstatus SET name_ru = ?, " +
            "name_en = ? WHERE id = ?";
    private static final String COULDN_T_GET_ORDER_STATUS = "Couldn't get order status";
    private static final String COULDN_T_SET_ORDER_STATUS = "Couldn't set order status";


    @Override
    protected String getTableName() {
        return ORDER_STATUS;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_ORDER_STATUS;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_ORDER_STATUS_BY_ID;
    }

    @Override
    protected OrderStatus getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        OrderStatus orderStatus = new OrderStatus();
        try {
            orderStatus.setRuName(resultSet.getString(NAME_RU));
            orderStatus.setEnName(resultSet.getString(NAME_EN));
            orderStatus.setId(resultSet.getInt(ID));
            orderStatus.setDeleted(resultSet.getBoolean(DELETED));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_ORDER_STATUS, e);
            throw new DAOException(COULDN_T_GET_ORDER_STATUS, e);
        }
        return orderStatus;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(OrderStatus orderStatus,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setString(2, orderStatus.getEnName());
            preparedStatement.setString(1, orderStatus.getRuName());

        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_ORDER_STATUS, e);
            throw new DAOException(COULDN_T_SET_ORDER_STATUS, e);
        }
    }
}
