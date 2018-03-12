package com.epam.musicstore.dao;


import com.epam.musicstore.entity.Image;
import com.epam.musicstore.entity.Product;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ImageDAO extends AbstractDAO<Image> {
    private static final Logger LOG = LoggerFactory.getLogger(ImageDAO.class);
    private static final String DELETED = "deleted";
    private static final String ID = "id";
    private static final String DATE_MODIFIED = "time_modified";
    private static final String PATH = "path";
    private static final String NAME = "name";
    private static final String PRODUCT_ID = "product_id";
    private static final String IMAGE = "image";
    private static final String INSERT_IMAGE = "INSERT INTO musicstore.image(name, product_id, path, " +
            "time_modified) VALUES(?, ?, ?, ?)";
    private static final String UPDATE_IMAGE_BY_ID = "UPDATE musicstore.image SET name = ?, product_id = ?, path = ?, " +
            "time_modified = ?  WHERE id = ?";
    private static final String COULDN_T_GET_IMAGE = "Couldn't get image";
    private static final String COULDN_T_SET_IMAGE = "Couldn't set Image";

    @Override
    protected String getTableName() {
        return IMAGE;
    }

    @Override
    protected String getQueryForInsert() {
        return INSERT_IMAGE;
    }

    @Override
    protected String getQueryForUpdate() {
        return UPDATE_IMAGE_BY_ID;
    }

    @Override
    protected Image getObjectFromResultSet(ResultSet resultSet) throws DAOException {
        Image image = new Image();
        try {
            Product product = new Product(resultSet.getInt(PRODUCT_ID));
            image.setName(resultSet.getString(NAME));
            image.setPath(resultSet.getString(PATH));
            image.setProduct(product);
            image.setTimeModified(new DateTime(resultSet.getTimestamp(DATE_MODIFIED)));
            image.setId(resultSet.getInt(ID));
            image.setDeleted(resultSet.getBoolean(DELETED));
        } catch (SQLException e) {
            LOG.error(COULDN_T_GET_IMAGE, e);
            throw new DAOException(COULDN_T_GET_IMAGE, e);
        }
        return image;
    }

    @Override
    protected void setVariablesForPreparedStatementExceptId(Image image, PreparedStatement preparedStatement)
            throws DAOException {
        try {
            preparedStatement.setString(1, image.getName());
            preparedStatement.setInt(2, image.getProduct().getId());
            preparedStatement.setString(3, image.getPath());
            preparedStatement.setTimestamp(4, new Timestamp(image.getTimeModified().getMillis()));
        } catch (SQLException e) {
            LOG.error(COULDN_T_SET_IMAGE, e);
            throw new DAOException(COULDN_T_SET_IMAGE, e);
        }
    }
}
