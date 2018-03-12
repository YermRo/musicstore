package com.epam.musicstore.action.user;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserCash implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditUserCash.class);
    private static final String REFILL = "refill";
    private static final String BALANCE = "balance";
    private static final String REFILLED_BALANCE = "{} refilled balance --> +{}";
    private static final String REFILL_ERROR = "Couldn't refill balance";
    private UserService userService = new UserService();
    private String cash;
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        try {
            String id = req.getParameter(UserConstants.USER_ID);
            cash = req.getParameter(BALANCE);
            Validate validation = new Validate();
            User user = userService.getUserByID(Integer.valueOf(id));
            if (validation.checkMoney(req, cash)) {
                req.setAttribute(UserConstants.USER, user);
                return new ActionResult(REFILL);
            }
            updateUserBalance(req, user);
            return new ActionResult(PageConstants.MANAGE_USERS_REDIRECT, true);
        } catch (ServiceException e) {
            LOG.info(REFILL_ERROR, e);
            throw new ActionException(REFILL_ERROR, e);
        }
    }

    private void updateUserBalance(HttpServletRequest req, User user) throws ServiceException {
        User updatedUser;
        User loggedUser = (User) req.getSession().getAttribute(UserConstants.LOGGED_USER);
        if (loggedUser.equals(user)) {
            updatedUser = userService.setUserCash(loggedUser.getId(), cash);
            req.getSession().setAttribute(UserConstants.LOGGED_USER, updatedUser);
        } else {
            updatedUser = userService.setUserCash(user.getId(), cash);
        }
        LOG.info(REFILLED_BALANCE, updatedUser,cash);
    }
}
