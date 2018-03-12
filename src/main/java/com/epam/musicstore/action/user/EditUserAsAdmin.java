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
import com.epam.musicstore.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserAsAdmin implements Action {
    private static final String UNABLE_REGISTER = "Couldn't register user";
    private static final Logger LOG = LoggerFactory.getLogger(EditUserAsAdmin.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        UserService userService = new UserService();
        Validate validation = new Validate();
        UserUtil userUtil = new UserUtil();
        User user;
        try {
            user = userService.getUserByID(Integer.valueOf(req.getParameter(UserConstants.USER_ID)));
            if (validation.validateUser(req)) {
                req.setAttribute(UserConstants.USER, user);
                req.setAttribute(UserConstants.ADDRESS, user.getAddress());
                return new ActionResult(PageConstants.EDIT_USER_AS_ADMIN);
            }
            userUtil.fillUserExceptPassword(req, user);
            userUtil.getFilledAddress(req, user);
            return new ActionResult(PageConstants.MANAGE_USERS_REDIRECT, true);
        } catch (ServiceException e) {
            LOG.info(UNABLE_REGISTER, e);
            throw new ActionException(UNABLE_REGISTER, e);
        }
    }
}
