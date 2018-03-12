package com.epam.musicstore.dao;

import com.epam.musicstore.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDAO extends AbstractDAO<Role> {
    private static final Logger LOG = LoggerFactory.getLogger(RoleDAO.class);
    private static final String ROLE_NAME = "role_name";
    private static final String ID = "id";
    private static final String DELETED = "deleted";
    private static final String ROLE = "role ";
    private static final String INSERT_ROLE = "INSERT INTO musicstore.role(name_ru, name_en, name) values(?, ?, ?)";
    private static final String UPDATE_ROLE_BY_ID = "UPDATE musicstore.role SET name_ru = ?, name_en = ?, name=?" +
            " WHERE id = ?";
    private static final String NAME_RU = "name_ru";
    private static final String NAME_EN = "name_en";
    private static final String NAME = "name";
        private static final String COULDN_T_GET_ROLE = "Couldn't get role";
    private static final String COULDN_T_SET_ROLE = "Couldn't set role";

    @Override
    protected String getTableName() {
        return ROLE;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_ROLE;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_ROLE_BY_ID;
    }

    @Override
    protected Role getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Role role = null;
        try{
            role = new Role();
            role.setId(resultSet.getInt(ID));
            role.setDeleted(resultSet.getBoolean(DELETED));
            role.setRuName(resultSet.getString(NAME_RU));
            role.setEnName(resultSet.getString(NAME_EN));
            role.setName(resultSet.getString(NAME));
        }
        catch (SQLException e){
            LOG.error(COULDN_T_GET_ROLE, e);
            throw new DAOException(COULDN_T_GET_ROLE, e);
        }
        return role;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Role role,
                                                            PreparedStatement preparedStatement) throws DAOException {
        try {
            preparedStatement.setString(2, role.getRuName());
            preparedStatement.setString(3, role.getEnName());
            preparedStatement.setString(3, role.getName());
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_ROLE, e);
            throw new DAOException(COULDN_T_SET_ROLE, e);
        }
    }
}
