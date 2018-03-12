package com.epam.musicstore.service;

import com.epam.musicstore.dao.BaseDAO;
import com.epam.musicstore.dao.DAOException;
import com.epam.musicstore.dao.DAOFactory;
import com.epam.musicstore.entity.*;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.*;

public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final String COULDN_T_REGISTER_USER = "Couldn't RegisterUser user";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String COULDN_T_GET_USER = "couldn't get user";
    private static final String COULDN_T_CHECK_EMAIL = "Couldn't check email";
    private static final String COULDN_T_GET_USER_ADDRESS = "Couldn't get user address";
    private static final String USER_UPDATED = "User updated";
    private static final String COULDN_T_UPDATE_USER = "couldn't update user";
    private static final String KZT = "KZT";
    private static final String CASH_UPDATED_SUCCESSFULLY = "Cash updated successfully";
    private static final String COULDN_T_UPDATE_CASH = "Couldn't update cash";
    private static final String USER_ID = "user_id";
    private static final String ORDER_ID = "order_id";
    private static final String COULDN_T_GET_USER_ORDERS = "Couldn't get user orders";
    private static final String COULDN_T_UPDATE_USER_ADDRESS = "Couldn't update user address";


    public User registerUser(User user, Address address) throws ServiceException {
        User registeredUser = null;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            registeredUser = registerUserToDB(daoFactory, user, address);
        } catch (DAOException e) {
            LOG.error(COULDN_T_REGISTER_USER, e);
        }
        return registeredUser;
    }

    private User registerUserToDB(DAOFactory daoFactory, User user, Address address) throws DAOException,
            ServiceException {
        User registeredUser = null;
       Address registeredAddress = null;
        try {
            daoFactory.startTransaction();
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            BaseDAO<Address> addressDao = daoFactory.getDAO(Address.class);
           registeredAddress = addressDao.insert(address);
            user.setAddress(registeredAddress);
            registeredUser = userDao.insert(user);
            daoFactory.commitTransaction();
        } catch (DAOException e) {
            LOG.error(COULDN_T_REGISTER_USER, e);
            daoFactory.rollbackTransaction();
        }
        return registeredUser;
    }

    public User logIn(String email, String password) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            Map<String, String> params = new HashMap<>();
            params.put(EMAIL, email);
            params.put(PASSWORD, password);
            BaseDAO<User> userDAO = daoFactory.getDAO(User.class);
            List<User> users = userDAO.getByParams(params);
            if (!users.isEmpty() && !users.get(0).isDeleted()) {
                return getUserByID(users.get(0).getId());
            }
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_USER);
            throw new ServiceException(e, COULDN_T_GET_USER);
        }
        return null;
    }

    public boolean checkEmail(String email) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDAO = daoFactory.getDAO(User.class);
            List<User> usersWithEnteredEmail = userDAO.getByParams(Collections.singletonMap(EMAIL, email));
            return usersWithEnteredEmail.isEmpty();
        } catch (DAOException e) {
            LOG.error(COULDN_T_CHECK_EMAIL);
            throw new ServiceException(e, COULDN_T_CHECK_EMAIL);
        }
    }

    public Address getUserAddress(User user) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            Address address;
            BaseDAO<Address> addressDAO = daoFactory.getDAO(Address.class);
            return addressDAO.getByID(user.getAddress().getId());
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_USER_ADDRESS);
            throw new ServiceException(e, COULDN_T_GET_USER_ADDRESS);
        }
    }

    public User updateUser(User user) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDAO = daoFactory.getDAO(User.class);
            userDAO.update(user);
            LOG.debug(USER_UPDATED);
        } catch (DAOException e) {
            LOG.error(COULDN_T_UPDATE_USER);
            throw new ServiceException(e, COULDN_T_UPDATE_USER);
        }
        return user;
    }

    public User setUserCash(int id, String cash) throws ServiceException {
        User user;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            user = updateCash(id, cash, daoFactory);
        } catch (DAOException e) {
            LOG.error(COULDN_T_UPDATE_CASH);
            throw new ServiceException(e, COULDN_T_UPDATE_CASH);
        }
        return user;
    }

    private User updateCash(int id, String cash, DAOFactory daoFactory) throws DAOException, ServiceException {
        User user;
        Money totalCash;

        try {
            daoFactory.startTransaction();
            BaseDAO<User> userDAO = daoFactory.getDAO(User.class);
            user = userDAO.getByID(id);
            totalCash = Money.parse(KZT + cash);
            user.setCash(totalCash);
            userDAO.update(user);
            daoFactory.commitTransaction();
            LOG.debug(CASH_UPDATED_SUCCESSFULLY);
        } catch (DAOException e) {
            LOG.error(COULDN_T_UPDATE_CASH);
            daoFactory.rollbackTransaction();
            throw new ServiceException(e, COULDN_T_UPDATE_CASH);
        }
        return user;
    }

    public List<Order> getUserOrders(int id) throws ServiceException {
        List<Order> orders;
        List<OrderingItem> orderingItems;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Order> orderDao = daoFactory.getDAO(Order.class);
            BaseDAO<OrderStatus> orderStatusDao = daoFactory.getDAO(OrderStatus.class);
            BaseDAO<OrderingItem> orderItemDao = daoFactory.getDAO(OrderingItem.class);
            BaseDAO<Product> productDao = daoFactory.getDAO(Product.class);
            orders = orderDao.getByParams(Collections.singletonMap(USER_ID, String.valueOf(id)));
            for (Order order : orders) {
                order.setStatus(orderStatusDao.getByID(order.getStatus().getId()));
                orderingItems = orderItemDao.getByParams(Collections.singletonMap(ORDER_ID, String
                        .valueOf(order.getId())));
                for (OrderingItem orderingItem : orderingItems) {
                    orderingItem.setProduct(productDao.getByID(orderingItem.getProduct().getId()));
                }
                order.setOrderingItems(orderingItems);
            }
        } catch (DAOException e) {
            LOG.error(COULDN_T_GET_USER_ORDERS);
            throw new ServiceException(e, COULDN_T_GET_USER_ORDERS);
        }
        return orders;
    }


    public Address updateUserAddress(Address address) throws ServiceException {
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<Address> addressDao = daoFactory.getDAO(Address.class);
            addressDao.update(address);
        } catch (DAOException e) {
            LOG.error(COULDN_T_UPDATE_USER_ADDRESS);
            throw new ServiceException(e, COULDN_T_UPDATE_USER_ADDRESS);
        }
        return address;
    }

    public User getUserByID(int id) throws ServiceException {
        User user;
        try (DAOFactory daoFactory = DAOFactory.getDAOFactory()) {
            BaseDAO<User> userDao = daoFactory.getDAO(User.class);
            BaseDAO<Address> addressDao = daoFactory.getDAO(Address.class);
            BaseDAO<Role> roleDao = daoFactory.getDAO(Role.class);
            user = userDao.getByID(id);
            user.setAddress(addressDao.getByID(user.getAddress().getId()));
            user.setRole(roleDao.getByID(user.getRole().getId()));

        }catch (DAOException e){
            LOG.error(COULDN_T_GET_USER);
            throw new ServiceException(e, COULDN_T_GET_USER);
        }
        return user;
    }
}
