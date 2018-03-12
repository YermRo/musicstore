package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.OrderStatus;
import com.epam.musicstore.entity.User;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDAO extends AbstractDAO<Order> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);
    private static final String USER_ID = "user_id";
    private static final String ORDER_STATUS_ID = "status_id";
    private static final String CREATED = "created";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String DESCRIPTION_RU = "description_ru";
    private static final String DESCRIPTION_EN = "description_en";
    private static final String ORDER = "musicstore.order";
    private static final String INSERT_ORDER = "INSERT INTO musicstore.order(user_id, created, status_id, " +
            "description) values(?, ?, ?, ?)";
    private static final String UPDATE_ORDER_BY_ID = "UPDATE musicstore.order SET user_id =? , created = ?, " +
            "status_id = ?, description = ? WHERE id = ?";
    private static final String COULDN_T_GET_ORDER = "Couldn't get order";
    private static final String COULDN_T_SET_ORDER = "Couldn't set order";
    private static final String DESCRIPTION = "description";


    @Override
    protected String getTableName() {
        return ORDER;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_ORDER;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_ORDER_BY_ID;
    }

    @Override
    protected Order getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Order order = null;
        try {
            User user = new User(resultSet.getInt(USER_ID));
            OrderStatus orderStatus = new OrderStatus(resultSet.getInt(ORDER_STATUS_ID));
            order = new Order.Builder(user)
                    .setStatus(orderStatus)
                    .setCreationTime(new DateTime(resultSet.getTimestamp(CREATED)))
                    .setUser(user)
                    .build();
            order.setId(resultSet.getInt(ID));
            order.setDeleted(resultSet.getBoolean(DELETED));
            order.setDescription(resultSet.getString(DESCRIPTION));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_ORDER, e);
            throw new DAOException(COULDN_T_GET_ORDER, e);
        }
        return order;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Order order, PreparedStatement preparedStatement)
            throws DAOException {
        try {
            preparedStatement.setInt(1, order.getUser().getId());
            preparedStatement.setTimestamp(2, new Timestamp(order.getCreationTime().getMillis()));
            preparedStatement.setInt(3, order.getStatus().getId());
            preparedStatement.setString(4, order.getDescription());

        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_ORDER, e);
            throw new DAOException(COULDN_T_SET_ORDER, e);
        }
    }
}
