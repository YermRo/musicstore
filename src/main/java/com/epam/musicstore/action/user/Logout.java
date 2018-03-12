package com.epam.musicstore.action.user;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Logout implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(Logout.class);
    private static final String LOGOUT = "{} logged out";


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        LOG.info(LOGOUT, req.getSession(false).getAttribute(UserConstants.LOGGED_USER));
        req.getSession(false).removeAttribute(UserConstants.LOGGED_USER);
        return new ActionResult(PageConstants.HOME, true);
    }
}
