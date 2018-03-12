package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Address;
import com.epam.musicstore.entity.Role;
import com.epam.musicstore.entity.User;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO<User> {
    private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);
    private static final String ADDRESS_ID = "address_id";
    private static final String ROLE_ID = "role_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String KZT = "KZT";
    private static final String CASH = "cash";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String PHONE_NUMBER = "phone_number";
    private static final String ID = "id";
    private static final String PASSWORD = "password";
    private static final String DELETED = "deleted";
    private static final String USER = "user ";
    private static final String INSERT_USER = "INSERT INTO musicstore.user(first_name, last_name, email, " +
            "password, phone_number, address_id, role_id, cash, gender) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER_BY_ID = "UPDATE musicstore.user SET  first_name = ?, " +
            "last_name = ?, email = ?, password = ?, phone_number = ?, address_id = ?, role_id = ?, cash = ?," +
            " gender = ? WHERE id = ?";
    private static final String COULDN_T_GET_USER = "Couldn't get user";
    private static final String COULDN_T_SET_USER = "Couldn't set user";

    @Override
    protected String getTableName() {
        return USER;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_USER;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_USER_BY_ID;
    }

    @Override
    protected User getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        User user = null;
        try {
            Address address = new Address(resultSet.getInt(ADDRESS_ID));
            Role role = new Role(resultSet.getInt(ROLE_ID));
            Money cash = Money.of(CurrencyUnit.getInstance(KZT), resultSet.getBigDecimal(CASH));
            user = new User.Builder(resultSet.getString(FIRST_NAME), resultSet.getString(LAST_NAME))
                    .setAddress(address)
                    .setCash(cash)
                    .setEmail(resultSet.getString(EMAIL))
                    .setGender((resultSet.getString(GENDER)))
                    .setPhoneNumber(resultSet.getString(PHONE_NUMBER))
                    .setPassword(resultSet.getString(PASSWORD))
                    .setRole(role)
                    .build();
            user.setId(resultSet.getInt(ID));
            user.setDeleted(resultSet.getBoolean(DELETED));
            user.setCash(Money.of(CurrencyUnit.getInstance(KZT), resultSet.getBigDecimal(CASH)));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_USER, e);
            throw new DAOException(COULDN_T_GET_USER, e);
        }
        return user;

    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(User user,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getPhoneNumber());
            preparedStatement.setInt(6, user.getAddress().getId());
            preparedStatement.setInt(7, user.getRole().getId());
            preparedStatement.setBigDecimal(8, user.getCash().getAmount());
            preparedStatement.setString(9, String.valueOf(user.getGender()));
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_USER, e);
            throw new DAOException(COULDN_T_SET_USER, e);
        }
    }




}