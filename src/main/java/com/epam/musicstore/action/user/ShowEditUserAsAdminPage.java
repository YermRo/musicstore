package com.epam.musicstore.action.user;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Address;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowEditUserAsAdminPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowEditUserAsAdminPage.class);
    private static final String USER_INFO = "{} - user";
    private static final String ERROR = "Couldn't show edit user page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        try {
            UserService userService = new UserService();
            User user = userService.getUserByID(Integer.valueOf(req.getParameter(UserConstants.ID)));
            Address address = userService.getUserAddress(user);
            LOG.info(USER_INFO, user);
            req.setAttribute(UserConstants.USER, user);
            req.setAttribute(UserConstants.ADDRESS, address);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        return new ActionResult(PageConstants.EDIT_USER_AS_ADMIN);
    }
}
