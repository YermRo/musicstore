package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO extends AbstractDAO<Address> {
    private static final Logger LOG = LoggerFactory.getLogger(AddressDAO.class);
    private static final String COUNTRY = "country";
    private static final String CITY = "city";
    private static final String STREET = "street";
    private static final String BUILDING_NUMBER = "building_number";
    private static final String APARTMENT_NUMBER = "apartment_number";
    private static final String POST_INDEX = "post_index";
    private static final String ADDRESS = "address ";
    private static final String INSERT_ADDRESS = "INSERT INTO musicstore.address(country, city, street," +
            " building_number, apartment_number, post_index) values(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ADDRESS_BY_ID = "UPDATE musicstore.address set country = ?," +
            " city = ?, street = ?, building_number = ?, apartment_number = ?, post_index = ? WHERE id = ?";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String COULDN_T_GET_ADDRESS = "Couldn't get address";
    private static final String GETTING_OBJECT_FROM_RESULT_SET = "Getting object from result set";
    private static final String COULDN_T_SET_ADDRESS = "Couldn't set address";

    @Override
    protected String getTableName() {
        return ADDRESS;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_ADDRESS;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_ADDRESS_BY_ID;
    }

    @Override
    protected Address getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Address address = null;
        try {
            address = new Address.Builder()
                    .setCountry(resultSet.getString(COUNTRY))
                    .setCity(resultSet.getString(CITY))
                    .setStreet(resultSet.getString(STREET))
                    .setBuildingNumber(resultSet.getString(BUILDING_NUMBER))
                    .setApartmentNumber(resultSet.getString(APARTMENT_NUMBER))
                    .setPostIndex(resultSet.getString(POST_INDEX))
                    .build();
            address.setId(resultSet.getInt(ID));
            address.setDeleted(resultSet.getBoolean(DELETED));
            LOG.debug(GETTING_OBJECT_FROM_RESULT_SET);
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_ADDRESS, e);
            throw new DAOException(COULDN_T_GET_ADDRESS, e);
        }
        return address;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Address address, PreparedStatement preparedStatement)
            throws DAOException {
        try {
            preparedStatement.setString(1, address.getCountry());
            preparedStatement.setString(2, address.getCity());
            preparedStatement.setString(3, address.getStreet());
            preparedStatement.setString(4, address.getBuildingNumber());
            preparedStatement.setString(5, address.getApartmentNumber());
            preparedStatement.setString(6, address.getPostIndex());
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_ADDRESS, e);
            throw new DAOException(COULDN_T_SET_ADDRESS, e);
        }
    }
}
