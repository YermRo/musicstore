package com.epam.musicstore.dao;




import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epam.musicstore.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDAO<T extends BaseEntity> implements BaseDAO<T> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractDAO.class);
    private static final String SELECT_FROM = "SELECT * FROM ";
    private static final String WHERE_ID = " WHERE id =";
    private static final String ORDER_BY_ID = " ORDER BY id ";
    private static final String SELECT_COUNT_FROM = "SELECT count(*) FROM ";
    private static final String WHERE_NOT_DELETED = " WHERE deleted = 0 ";
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String COULDN_T_SET_VARIABLES_FOR_PREPARED_STATEMENT = "Couldn't set variables for prepared statement";
    private static final String INSERTED = "Inserted";
    private static final String COULDN_T_INSERT_OBJECT_TO_DB = "Couldn't insert Object to DB";
    private static final String GETTING_OBJECT_WITH_ID = "Getting object with ID";
    private static final String COULDN_T_FIND_OBJECT_BY_CURRENT_ID = "Couldn't find object by current id";
    private static final String GETTING_OBJECTS_LIST_BY_PARAMS = "Getting objects list by params";
    private static final String COULDN_T_GET_OBJECT_LIST_BY_PARAMS = "Couldn't get object list by params";
    private static final String GETTING_ALL_OBJECT_LIST = "Getting all object list";
    private static final String COULDN_T_GET_ALL_OBJECT_LIST = "Couldn't get all object list";
    private static final String UPDATING = "Updating";
    private static final String COULDN_T_UPDATE_OBJECT_IN_DB = "Couldn't update object in DB";
    private static final String COULD_T_DELETE_OBJECT = "Could't delete object";
    private static final String TABLE_HAS_NOT_DELETED_ROWS = "Table has not deleted rows";
    private static final String COULDN_T_GET_COUNT = "Couldn't get count";
    private static final String OBJECT_DELETED_FROM_TABLE_BY_ID = "Object deleted from table by ID";
    private static final String MUSICSTORE_STORAGE_ITEM = "musicstore.storage_item ";
    private static final String WHERE_PRODUCT_ID = " WHERE product_id = ";
    private Connection connection;

    public AbstractDAO() {
    }

    void setConnection(Connection connection) {
        this.connection = connection;
    }


    protected abstract String getTableName();

    protected abstract String getQueryForInsert();

    protected abstract String getQueryForUpdate();

    protected abstract T getObjectFromResultSet(ResultSet resultSet) throws DAOException;


    protected abstract void setVariablesForPreparedStatementExceptId(T entity, PreparedStatement preparedStatement)
            throws DAOException;

    private void setVariablesForPreparedStatement(T entity, PreparedStatement preparedStatement) throws DAOException {
        setVariablesForPreparedStatementExceptId(entity, preparedStatement);
        int lastParameter;
        try {
            lastParameter = preparedStatement.getParameterMetaData().getParameterCount();
            preparedStatement.setInt(lastParameter, entity.getId());
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_VARIABLES_FOR_PREPARED_STATEMENT);
            throw  new DAOException(COULDN_T_SET_VARIABLES_FOR_PREPARED_STATEMENT, e);
        }
    }

    @Override
    public T insert(T t) throws DAOException {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getQueryForInsert(),
                    Statement.RETURN_GENERATED_KEYS) ;
            setVariablesForPreparedStatementExceptId(t, preparedStatement);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            t.setId(resultSet.getInt(1));
            LOG.debug(INSERTED, t);
        } catch (SQLException e) {
             throw  new DAOException(COULDN_T_INSERT_OBJECT_TO_DB, e);
        }

        return t;
    }

    @Override
    public T getByID(Integer id) throws DAOException {
        T object = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM).append(getTableName()).append(WHERE_ID).append(id);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            resultSet.next();
            object = getObjectFromResultSet(resultSet);
            LOG.debug(GETTING_OBJECT_WITH_ID, object, id);

        } catch (SQLException e) {
            LOG.error(COULDN_T_FIND_OBJECT_BY_CURRENT_ID, e);
            throw new DAOException(COULDN_T_FIND_OBJECT_BY_CURRENT_ID, e);
        }
        return object;
    }

    @Override
    public List<T> getByParams(Map<String, String> params) throws DAOException {
        List<T> objects = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(createQueryForFindByParams(params))) {
            while (resultSet.next()) {
                objects.add(getObjectFromResultSet(resultSet));
            }
            LOG.debug(GETTING_OBJECTS_LIST_BY_PARAMS, params, objects);
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_OBJECT_LIST_BY_PARAMS, e);
            throw new DAOException(COULDN_T_GET_OBJECT_LIST_BY_PARAMS, e);
        }
        return objects;
    }


    @Override
    public List<T> getAll(int pageNumber, int pageSize) throws DAOException {
        List<T> objects = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append(SELECT_FROM).append(getTableName()).append(" WHERE deleted=0 LIMIT ? OFFSET ?");
        try (PreparedStatement preparedStatement = connection.prepareStatement(builder.toString())) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, (pageNumber - 1) * pageSize);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                objects.add(getObjectFromResultSet(rs));
                LOG.debug("Get object list from page number - {} and page siae - {} - {}", pageNumber, pageSize, objects);
            }
        } catch (SQLException e) {
            LOG.info(COULDN_T_GET_ALL_OBJECT_LIST, e);
            throw new DAOException(COULDN_T_GET_ALL_OBJECT_LIST, e);
        }
        return objects;
    }

    @Override
    public List<T> getAll() throws DAOException {
        List<T> objects = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SELECT_FROM).append(getTableName()).append(ORDER_BY_ID);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            while (resultSet.next()) {
                objects.add(getObjectFromResultSet(resultSet));
            }
            LOG.info(stringBuilder.toString());
            LOG.debug(GETTING_ALL_OBJECT_LIST, objects);
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_ALL_OBJECT_LIST, e);
            throw new DAOException(COULDN_T_GET_ALL_OBJECT_LIST, e);
        }
        return objects;
    }

    @Override
    public void update(T entity) throws  DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(getQueryForUpdate())) {
            setVariablesForPreparedStatement(entity, preparedStatement);
            preparedStatement.executeUpdate();
            LOG.debug(UPDATING, entity);
        } catch (SQLException e) {
            LOG.error(COULDN_T_UPDATE_OBJECT_IN_DB, e);
            throw new DAOException(COULDN_T_GET_ALL_OBJECT_LIST, e);
        }
    }

    @Override
    public void deleteByID(Integer id) throws DAOException{


        try (Statement statement = connection.createStatement()) {
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ").append(getTableName()).append(" SET deleted = 1").append(WHERE_ID).append(id);
            statement.executeUpdate(builder.toString());
            LOG.debug(OBJECT_DELETED_FROM_TABLE_BY_ID, getTableName(), id);
        } catch (SQLException e) {
            LOG.error(COULD_T_DELETE_OBJECT, e);
            throw new DAOException(COULD_T_DELETE_OBJECT, e);
        }




    }

    @Override
    public int getNotDeletedCount() throws DAOException{
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        stringBuilder.append(SELECT_COUNT_FROM).append(getTableName()).append(WHERE_NOT_DELETED);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(stringBuilder.toString())) {
            resultSet.next();
            count = resultSet.getInt(1);
            LOG.debug(TABLE_HAS_NOT_DELETED_ROWS, getTableName(), count);
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_COUNT, e);
            throw new DAOException(COULDN_T_GET_COUNT, e);
        }
        return count;
    }

    private String createQueryForFindByParams(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM).append(getTableName()).append(WHERE);
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (params.size() == 1) {
                query.append(param.getKey()).append(" = '").append(param.getValue()).append("'");

                return query.toString();
            } else {
                query.append(param.getKey()).append(" = '").append(param.getValue()).append("'").append(AND);
            }
        }
        LOG.debug("result query", query);
        return query.substring(0, query.length() - AND.length());

    }

    @Override
    public void deleteStorageItemByProductID(Integer id) throws DAOException{


        try (Statement statement = connection.createStatement()) {
            StringBuilder builder = new StringBuilder();
            builder.append("UPDATE ").append(MUSICSTORE_STORAGE_ITEM).append(" SET deleted = 1").append(WHERE_PRODUCT_ID).append(id);
            statement.executeUpdate(builder.toString());
            LOG.debug(OBJECT_DELETED_FROM_TABLE_BY_ID, getTableName(), id);
        } catch (SQLException e) {
            LOG.error(COULD_T_DELETE_OBJECT, e);
            throw new DAOException(COULD_T_DELETE_OBJECT, e);
        }




    }



}
