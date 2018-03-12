package com.epam.musicstore.action.user;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.ErrorConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUser implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteUser.class);
    private static final String DELETE_ERROR = "Couldn't delete user by id";
    private static final String DELETE_LOG_ERROR = "{} tried to delete himself when logged in";
    private static final String LOG_DELETED = "{} - has been deleted";
    private static final String AFTERE_USER_DELETE_FLAG = "afterUserDeleteFlag";
    private static final String TRUE = "true";
    private static final String USER_DELETE_ERROR = "userDeleteError";


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        User user = (User) req.getSession().getAttribute(UserConstants.LOGGED_USER);
        String id = req.getParameter(UserConstants.ID);
        if (id.equals(String.valueOf(user.getId()))) {
            req.setAttribute(USER_DELETE_ERROR, ErrorConstants.TRUE);
            LOG.info(DELETE_LOG_ERROR, user);
            return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
        }
        try {
            ShopService shopService = new ShopService();
            shopService.deleteUserById(id);
            LOG.info(LOG_DELETED, user);
            req.setAttribute(AFTERE_USER_DELETE_FLAG, TRUE);
            return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
        } catch (ServiceException e) {
            LOG.info(DELETE_ERROR, e);
            throw new ActionException(DELETE_ERROR, e);
        }
    }
}
