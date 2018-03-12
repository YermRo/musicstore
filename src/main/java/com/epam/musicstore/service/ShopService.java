package com.epam.musicstore.service;
import com.epam.musicstore.dao.BaseDAO;
import com.epam.musicstore.dao.DAOException;
import com.epam.musicstore.dao.DAOFactory;
import com.epam.musicstore.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShopService {
    private static final String PRODUCT_ID = "product_id";
    private static final Logger LOG = LoggerFactory.getLogger(ShopService.class);
    private static final String COULDN_T_GET_ORDER = "Couldn't get order";
    private static final String ORDER_ID = "order_id";
    private static final String COULDN_T_GET_PRODUCT_TYPES_LIST = "Couldn't get product types list";
    private static final String COULDN_T_GET_PRODUCTS_COUNT = "couldn't get products count";
    private static final String COULDN_T_BUY_ORDER = "Couldn't buy order";
    private static final int ONE = 1;
    private static final String COULDN_T_GET_USERS_COUNT = "Couldn't get users count";
    private static final String COULDN_T_GET_ORDERS_COUNT = "Couldn't get orders count";
    private static final String COULDN_T_GET_ITEMS_COUNT = "Couldn't get items count";
    private static final String COULDN_T_GET_ORDER_STATUSES = "Couldn't get order statuses";
    private static final String COULDN_T_UPDATE_ORDER_STATUS = "Couldn't update order status";
    private static final String COULDN_T_DELETE_ORDER = "Couldn't delete order";
    private static final String COULDN_T_DELETE_PRODUCT = "Couldn't delete product";
    private static final String COULDN_T_DELETE_USER = "Couldn't delete user";
    private static final String COULDN_T_DELETE_STORAGE_ITEM = "Couldn't delete storage item";
    private static final String COULDN_T_UPDATE_STORAGE_ITEM = "Couldn't update storage item";
    private static final String COULDN_T_GET_PRODUCTS_ON_PAGE = "Couldn't get products on page";
    private static final String COULDN_T_GET_USERS = "Couldn't get users";
    private static final String COULDN_T_GET_STORAGE_ITEMS_ON_PAGE = "couldn't get storage items on page";
    private static final String TYPE_ID = "type_id";
    private static final String COULDN_T_GET_STORAGE_ITEM_BY_ID = "Couldn't get storage item by id";
    private static final String COULDN_T_GET_ORDERS_ON_PAGE = "Couldn't get orders on page";
    private static final String CAN_T_DELETE_STORAGE_ITEM_BY_PRODUCT_ID = "Can't delete storage item by product id";
    private static final String STORAGE_ID = "storage_id";

    public Order getOrder(int id) throws ServiceException {
        Order order;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = daoFactory.getDAO(Order.class);
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            BaseDAO<OrderingItem> orderingItemDao = daoFactory.getDAO(OrderingItem.class);
            BaseDAO<OrderStatus> orderStatusDao = daoFactory.getDAO(OrderStatus.class);
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            order = orderDao.getByID(id);
            order.setUser(userDao.getByID(order.getUser().getId()));
            order.setStatus(orderStatusDao.getByID(order.getStatus().getId()));
            List<OrderingItem> orderingItems = orderingItemDao.getByParams(Collections.singletonMap(ORDER_ID,
                    String.valueOf(order.getId())));
            for (OrderingItem item : orderingItems) {
                item.setProduct(productDao.getByID(item.getProduct().getId()));
            }
            order.setOrderingItems(orderingItems);
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_ORDER, e);
            throw new ServiceException(e, COULDN_T_GET_ORDER);
        }
        return order;
    }

    public List<ProductType> getAllProductTypes() throws ServiceException {
        List<ProductType> productTypes;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()){
           BaseDAO<ProductType> productTypeDao = daoFactory.getDAO(ProductType.class);
            productTypes = productTypeDao.getAll();
            productTypes = productTypes.stream().filter(productType ->
                    !productType.isDeleted()).collect(Collectors.toList());
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_PRODUCT_TYPES_LIST, e);
            throw new ServiceException(e, COULDN_T_GET_PRODUCT_TYPES_LIST);
        }
        return productTypes;
    }

    public int getProductsCount() throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            return productDao.getNotDeletedCount();
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_PRODUCTS_COUNT, e);
            throw new ServiceException(e, COULDN_T_GET_PRODUCTS_COUNT);
        }
    }

    public User buyCart(Order order) throws ServiceException {
        User user;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            user = buyOrder(jdbcDaoFactory, order);
        } catch (DAOException e) {
            LOG.info(COULDN_T_BUY_ORDER, e);
            throw new ServiceException(e, COULDN_T_BUY_ORDER);
        }
        return user;
    }

    private User buyOrder(DAOFactory daoFactory, Order order) throws DAOException, ServiceException {
        User user;
        try {
            daoFactory.startTransaction();
            BaseDAO<OrderStatus> orderStatusDao = daoFactory.getDAO(OrderStatus.class);
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            BaseDAO<OrderingItem> orderingItemDao = daoFactory.getDAO(OrderingItem.class);
            BaseDAO<Order> orderDao = daoFactory.getDAO(Order.class);
            BaseDAO<StorageItem> storageItemDao = daoFactory.getDAO(StorageItem.class);
            user = userDao.getByID(order.getUser().getId());
            user.spendCash(order.getPrice());
            userDao.update(user);
            order.setStatus(orderStatusDao.getByID(ONE));
            Order newOrder = orderDao.insert(order);
            for (OrderingItem orderingItem : order.getOrderingItems()) {
               StorageItem storageItem = storageItemDao.getByParams(Collections.singletonMap(PRODUCT_ID,
                        orderingItem.getProduct().getId().toString())).get(0);
               storageItem.setAmount(storageItem.getAmount()-orderingItem.getAmount());
               storageItemDao.update(storageItem);
                orderingItem.setOrder(newOrder);
                orderingItemDao.insert(orderingItem);
            }
            daoFactory.commitTransaction();
        } catch (DAOException e) {
            LOG.info(COULDN_T_BUY_ORDER, e);
            daoFactory.rollbackTransaction();
            throw new ServiceException(e, COULDN_T_BUY_ORDER);
        }
        return user;
    }

    public int getUsersCount() throws ServiceException {
        int usersCount;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            usersCount = userDao.getNotDeletedCount();
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_USERS_COUNT, e);
            throw new ServiceException(e, COULDN_T_GET_USERS_COUNT);
        }
        return usersCount;
    }

    public int getOrdersCount() throws ServiceException {
        int ordersCount;
        try ( DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = daoFactory.getDAO(Order.class);
            ordersCount = orderDao.getNotDeletedCount();
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_ORDERS_COUNT, e);
            throw new ServiceException(e, COULDN_T_GET_ORDERS_COUNT);
        }
        return ordersCount;
    }

    public int getStorageItemsCount() throws ServiceException {
        int storageItemsCount;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = daoFactory.getDAO(StorageItem.class);
            storageItemsCount = storageItemDao.getNotDeletedCount();
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_ITEMS_COUNT, e);
            throw new ServiceException(e, COULDN_T_GET_ITEMS_COUNT);
        }
        return storageItemsCount;
    }

    public List<OrderStatus> getAllOrderStatuses() throws ServiceException {
        List<OrderStatus> orderStatuses;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<OrderStatus> orderStatusDao = jdbcDaoFactory.getDAO(OrderStatus.class);
            orderStatuses = orderStatusDao.getAll();
            orderStatuses = orderStatuses.stream().filter(status -> !status.isDeleted()).collect(Collectors.toList());
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_ORDER_STATUSES, e);
            throw new ServiceException(e, COULDN_T_GET_ORDER_STATUSES);
        }
        return orderStatuses;
    }

    public void updateOrderStatus(String orderId, String statusId) throws ServiceException {
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = jdbcDaoFactory.getDAO(Order.class);
            Order order = orderDao.getByID(Integer.valueOf(orderId));
            order.getStatus().setId(Integer.valueOf(statusId));
            orderDao.update(order);
        } catch (DAOException e) {
            LOG.info(COULDN_T_UPDATE_ORDER_STATUS, e);
            throw new ServiceException(e, COULDN_T_UPDATE_ORDER_STATUS);
        }
    }

    public void updateStorageItem(String itemId, String amount) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = daoFactory.getDAO(StorageItem.class);
            StorageItem item = storageItemDao.getByID(Integer.valueOf(itemId));
            item.setAmount(Integer.valueOf(amount));
            storageItemDao.update(item);
        } catch (DAOException e) {
            LOG.info(COULDN_T_UPDATE_STORAGE_ITEM, e);
            throw new ServiceException(e, COULDN_T_UPDATE_STORAGE_ITEM);
        }
    }

    public void deleteOrderById(String id) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = daoFactory.getDAO(Order.class);
            orderDao.deleteByID(Integer.valueOf(id));
        } catch (DAOException e) {
            LOG.info(COULDN_T_DELETE_ORDER, e);
            throw new ServiceException(e, COULDN_T_DELETE_ORDER);
        }
    }

    public void deleteProductById(String id) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            productDao.deleteByID(Integer.valueOf(id));
        } catch (DAOException e) {
            LOG.info(COULDN_T_DELETE_PRODUCT, e);
            throw new ServiceException(e, COULDN_T_DELETE_PRODUCT);
        }
    }



    public void deleteUserById(String id) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            userDao.deleteByID(Integer.valueOf(id));
        } catch (DAOException e) {
            LOG.info(COULDN_T_DELETE_USER, e);
            throw new ServiceException(e, COULDN_T_DELETE_USER);
        }
    }

    public void deleteStorageItemById(String id) throws ServiceException {
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = jdbcDaoFactory.getDAO(StorageItem.class);
            storageItemDao.deleteByID(Integer.valueOf(id));
        } catch (DAOException e) {
            LOG.info(COULDN_T_DELETE_STORAGE_ITEM, e);
            throw new ServiceException(e, COULDN_T_DELETE_STORAGE_ITEM);
        }
    }

    public void deleteStorageItemByProductId(String id) throws ServiceException {
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = jdbcDaoFactory.getDAO(StorageItem.class);
            storageItemDao.deleteStorageItemByProductID(Integer.valueOf(id));
        } catch (DAOException e) {
            LOG.info(CAN_T_DELETE_STORAGE_ITEM_BY_PRODUCT_ID, e);
            throw new ServiceException(e, CAN_T_DELETE_STORAGE_ITEM_BY_PRODUCT_ID);
        }
    }


    public List<Product> getAllProductsOnPage(int pageSize, int pageNumber) throws ServiceException {
        List<Product> products;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Product> productDao = jdbcDaoFactory.getDAO(Product.class);
            BaseDAO<ProductType> productTypeDao = jdbcDaoFactory.getDAO(ProductType.class);
            products = productDao.getAll(pageNumber, pageSize);
            for (Product product : products) {
                int idCheck = product.getType().getId();
                product.setType(productTypeDao.getByID(product.getType().getId()));
            }
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_PRODUCTS_ON_PAGE, e);
            throw new ServiceException(e, COULDN_T_GET_PRODUCTS_ON_PAGE);
        }
        return products;
    }

    public List<User.Gender> getAllGenders() throws ServiceException{
        List<User.Gender> genders;

        genders = Arrays.asList(User.Gender.values());
        return genders;
    }


    public List<User> getAllUsersOnPage(int pageNumber, int pageSize) throws ServiceException {
        List<User> users;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDao = jdbcDaoFactory.getDAO(User.class);
            users = userDao.getAll(pageNumber, pageSize);
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_USERS, e);
            throw new ServiceException(e, COULDN_T_GET_USERS);
        }
        return users;
    }

    public List<StorageItem> getAllStorageItemsOnPage(int pageSize, int pageNumber) throws ServiceException {
        List<StorageItem> storageItems;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = jdbcDaoFactory.getDAO(StorageItem.class);
            BaseDAO<Product> productDao = jdbcDaoFactory.getDAO(Product.class);
            BaseDAO<Storage> storageDao = jdbcDaoFactory.getDAO(Storage.class);
            storageItems = storageItemDao.getAll(pageNumber, pageSize);
            for (StorageItem storageItem : storageItems) {
                storageItem.setStorage(storageDao.getByID(storageItem.getStorage().getId()));
                storageItem.setProduct(productDao.getByID(storageItem.getProduct().getId()));
            }
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_STORAGE_ITEMS_ON_PAGE, e);
            throw new ServiceException(e, COULDN_T_GET_STORAGE_ITEMS_ON_PAGE);
        }
        return storageItems;
    }

    public List<StorageItem> getAllStorageItems()
            throws ServiceException {
        List<StorageItem> storageItems;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = jdbcDaoFactory.getDAO(StorageItem.class);
            BaseDAO<Product> productDao = jdbcDaoFactory.getDAO(Product.class);
            BaseDAO<Storage> storageDao = jdbcDaoFactory.getDAO(Storage.class);
            storageItems = storageItemDao.getAll();
            for (StorageItem storageItem : storageItems) {
                storageItem.setStorage(storageDao.getByID(storageItem.getStorage().getId()));
                storageItem.setProduct(productDao.getByID(storageItem.getProduct().getId()));
            }
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_STORAGE_ITEMS_ON_PAGE, e);
            throw new ServiceException(e, COULDN_T_GET_STORAGE_ITEMS_ON_PAGE);
        }
        return storageItems;
    }

    public StorageItem getStorageItemByID(int id) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = daoFactory.getDAO(StorageItem.class);
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            BaseDAO<Storage> storageDao = daoFactory.getDAO(Storage.class);
            StorageItem storageItem = storageItemDao.getByID(id);
            storageItem.setStorage(storageDao.getByID(storageItem.getStorage().getId()));
            storageItem.setProduct(productDao.getByID(storageItem.getProduct().getId()));
            return storageItem;
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_STORAGE_ITEM_BY_ID);
            throw new ServiceException(e, COULDN_T_GET_STORAGE_ITEM_BY_ID);
        }
    }

    public StorageItem getStorageItemByProductId(int id) throws ServiceException {
        HashMap<String, String> map = new HashMap<>();
        map.put(PRODUCT_ID, String.valueOf(id));
        map.put(STORAGE_ID, String.valueOf(1));
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<StorageItem> storageItemDao = daoFactory.getDAO(StorageItem.class);
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            BaseDAO<Storage> storageDao = daoFactory.getDAO(Storage.class);
            StorageItem storageItem = storageItemDao.getByParams(map).get(0);
            storageItem.setStorage(storageDao.getByID(storageItem.getStorage().getId()));
            storageItem.setProduct(productDao.getByID(storageItem.getProduct().getId()));
            return storageItem;
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_STORAGE_ITEM_BY_ID);
            throw new ServiceException(e, COULDN_T_GET_STORAGE_ITEM_BY_ID);
        }
    }

    public List<Order> getAllOrdersOnPage(int pageSize, int pageNumber) throws ServiceException {
        List<Order> orders;
        try (DAOFactory jdbcDaoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = jdbcDaoFactory.getDAO(Order.class);
            BaseDAO <OrderingItem> orderingItemDao = jdbcDaoFactory.getDAO(OrderingItem.class);
            BaseDAO <OrderStatus> orderStatusDao = jdbcDaoFactory.getDAO(OrderStatus.class);
            BaseDAO <User> userDao = jdbcDaoFactory.getDAO(User.class);
            BaseDAO <Product> productDao = jdbcDaoFactory.getDAO(Product.class);
            orders = orderDao.getAll(pageNumber, pageSize);
            for (Order order : orders) {
                order.setUser(userDao.getByID(order.getUser().getId()));
                order.setStatus(orderStatusDao.getByID(order.getStatus().getId()));
                List<OrderingItem> orderingItems = orderingItemDao.getByParams(Collections.singletonMap(ORDER_ID,
                        String.valueOf(order.getId())));
                for (OrderingItem orderingItem : orderingItems) {
                    orderingItem.setProduct(productDao.getByID(orderingItem.getProduct().getId()));
                }
                order.setOrderingItems(orderingItems);
            }
        } catch (DAOException e) {
            LOG.info(COULDN_T_GET_ORDERS_ON_PAGE, e);
            throw new ServiceException(e, COULDN_T_GET_ORDERS_ON_PAGE);
        }
        return orders;
    }

}
