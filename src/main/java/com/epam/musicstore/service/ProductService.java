package com.epam.musicstore.service;

import com.epam.musicstore.dao.BaseDAO;
import com.epam.musicstore.dao.DAOException;
import com.epam.musicstore.dao.DAOFactory;
import com.epam.musicstore.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);
    private static final String COULDN_T_GET_PRODUCT_BY_ID = "Couldn't get product by id";
    private final String COULDN_T_ADD_PRODUCT_TO_DATABASE = "Couldn't add product to database";
    private static final String COULDN_T_ADD_STORAGE_ITEM = "Couldn't add storage item";
    private static final String TYPE_ID = "type_id";
    private static final String COULDN_T_GET_PRODUCTS_BY_TYPE = "Couldn't get products by type";
    private static final String PRODUCT_ID = "product_id";
    private static final String COULDN_T_GET_PREVIEW_IMAGE = "Couldn't get preview image";
    private static final String COULDN_T_GET_FILLED_PRODUCT = "Couldn't get filled product";
    private static final String COULDN_T_UPDATE_PRODUCT = "Couldn't update product";
    private static final String COULDN_T_UPDATE_PRODUCT_IMAGE = "Couldn't update product image";

    public Product getProductByID(int id) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            return productDao.getByID(id);
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_PRODUCT_BY_ID);
            throw new ServiceException(e, COULDN_T_GET_PRODUCT_BY_ID);
        }
    }

    public Product addProduct(Product product, Image image) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            addProductToDB(product, image, daoFactory);
        } catch (DAOException e) {
            LOG.error(COULDN_T_ADD_PRODUCT_TO_DATABASE);
            throw new ServiceException(e, COULDN_T_ADD_PRODUCT_TO_DATABASE);
        }
        return product;
    }

    private Product addProductToDB(Product product, Image image, DAOFactory daoFactory) throws ServiceException {
        Product addedProduct;
        try {
            daoFactory.startTransaction();
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            BaseDAO<Image> imageDao = daoFactory.getDAO(Image.class);
            addedProduct = productDao.insert(product);
            image.setProduct(addedProduct);
            imageDao.insert(image);
            daoFactory.commitTransaction();
        } catch (DAOException e) {
            LOG.error(COULDN_T_ADD_PRODUCT_TO_DATABASE);
            daoFactory.rollbackTransaction();
            throw new ServiceException(e, COULDN_T_GET_PRODUCT_BY_ID);
        }
        return product;
    }

    public void setProductAsStorageItem(Product product) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDAO = daoFactory.getDAO(StorageItem.class);
            StorageItem storageItem = new StorageItem();
            storageItem.setProduct(product);
            storageItem.setStorage(new Storage(1));
            storageItem.setAmount(0);
            storageItemDAO.insert(storageItem);
        } catch (DAOException e) {
            LOG.error(COULDN_T_ADD_STORAGE_ITEM);
            throw new ServiceException(e, COULDN_T_ADD_STORAGE_ITEM);
        }
    }

    public List<Product> getAllProductsByType(String typeId) throws ServiceException {
        List<Product> productList = new ArrayList<>();
        List<Product> productListWithoutDeleted = new ArrayList<>();
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            productList = productDao.getByParams(Collections.singletonMap(TYPE_ID, typeId));
            for (Product product : productList) {
                if (!product.isDeleted()) {
                    productListWithoutDeleted.add(product);
                }
            }
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_PRODUCTS_BY_TYPE);
            throw new ServiceException(e, COULDN_T_GET_PRODUCTS_BY_TYPE);
        }
        return productListWithoutDeleted;
    }

    public Image getProductPreviewImage(String id) throws ServiceException {
        List<Image> images;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Image> imageDao = daoFactory.getDAO(Image.class);
            images = imageDao.getByParams(Collections.singletonMap(PRODUCT_ID, id));
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_PREVIEW_IMAGE, e);
            throw new ServiceException(e, COULDN_T_GET_PREVIEW_IMAGE);
        }
        return images.get(0);
    }

    public Product getFilledProduct(String id) throws ServiceException {
        Product product = null;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            product = fillProduct(id, daoFactory);
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_FILLED_PRODUCT, e);
            throw new ServiceException(e, COULDN_T_GET_FILLED_PRODUCT);
        }
        return product;
    }

    private Product fillProduct(String id,  DAOFactory daoFactory) throws ServiceException, DAOException {
        Product product;
        try {
            daoFactory.startTransaction();
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            BaseDAO<Image> imageDao = daoFactory.getDAO(Image.class);
            BaseDAO<ProductType> productTypeDao = daoFactory.getDAO(ProductType.class);
            product = productDao.getByID(Integer.parseInt(id));
            ProductType productType = productTypeDao.getByID(product.getType().getId());
            product.setType(productType);
            Map<String, String> productParam = Collections.singletonMap(PRODUCT_ID, id);
            List<Image> images = imageDao.getByParams(productParam);
            product.setImages(images);
            daoFactory.commitTransaction();
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_FILLED_PRODUCT, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(e, COULDN_T_GET_FILLED_PRODUCT);
        }
        return product;
    }

    public void updateProduct(Product product) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            productDao.update(product);
        } catch (DAOException e) {
            LOG.info(COULDN_T_UPDATE_PRODUCT, e);
            throw new ServiceException(e, COULDN_T_UPDATE_PRODUCT);
        }
    }

    public void updateProductImage(Image image) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Image> imageDao = daoFactory.getDAO(Image.class);
            imageDao.update(image);
        } catch (DAOException e) {
            LOG.info(COULDN_T_UPDATE_PRODUCT_IMAGE, e);
            throw new ServiceException(e, COULDN_T_UPDATE_PRODUCT_IMAGE);
        }
    }


}


