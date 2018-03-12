package com.epam.musicstore.action.common;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowRefillPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowRefillPage.class);
    private static final String ERROR = "Couldn't show user  profile page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        try {
            String id = req.getParameter(UserConstants.ID);
            UserService userService = new UserService();
            User user = userService.getUserByID(Integer.valueOf(id));
            req.setAttribute(UserConstants.USER, user);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR);
        }
        return new ActionResult(PageConstants.REFILL);
    }
}
