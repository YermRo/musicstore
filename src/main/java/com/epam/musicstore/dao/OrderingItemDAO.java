package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.OrderingItem;
import com.epam.musicstore.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderingItemDAO extends AbstractDAO<OrderingItem> {
    private static final Logger LOG = LoggerFactory.getLogger(OrderingItemDAO.class);
    private static final String PRODUCT_ID = "product_id";
    private static final String ORDER_ID = "order_id";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String AMOUNT = "amount";
    private static final String ORDERING_ITEM = "ordering_item";
    private static final String INSERT_ORDERING_ITEM = "INSERT INTO musicstore.ordering_item( order_id, product_id,  " +
            "amount) values(?, ?, ?)";
    private static final String UPDATE_ORDERING_ITEM_BY_ID = "UPDATE musicstore.ordering_item SET order_id = ?, " +
            "product_id = ?, amount = ? WHERE ID = ?";
    private static final String COULDN_T_SET_ORDER_ITEM = "Couldn't set order item";
    private static final String COULDN_T_GET_ORDERING_ITEM = "Couldn't get ordering item";


    @Override
    protected String getTableName() {
        return ORDERING_ITEM;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_ORDERING_ITEM;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_ORDERING_ITEM_BY_ID;
    }

    @Override
    protected OrderingItem getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        OrderingItem orderingItem = new OrderingItem();
        try {
            Product product = new Product(resultSet.getInt(PRODUCT_ID));
            Order order = new Order(resultSet.getInt(ORDER_ID));
            orderingItem.setId(resultSet.getInt(ID));
            orderingItem.setDeleted(resultSet.getBoolean(DELETED));
            orderingItem.setProduct(product);
            orderingItem.setAmount(resultSet.getInt(AMOUNT));
            orderingItem.setOrder(order);
        }
        catch (SQLException e){
            LOG.error(COULDN_T_GET_ORDERING_ITEM, e);
            throw new DAOException(COULDN_T_GET_ORDERING_ITEM, e);
        }
        return  orderingItem;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(OrderingItem orderingItem,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setInt(1, orderingItem.getOrder().getId());
            preparedStatement.setInt(2,orderingItem.getProduct().getId());
            preparedStatement.setInt(3, orderingItem.getAmount());
        }
        catch (SQLException e){
            LOG.error(COULDN_T_SET_ORDER_ITEM, e);
            throw new DAOException(COULDN_T_SET_ORDER_ITEM, e);
        }
    }
}
