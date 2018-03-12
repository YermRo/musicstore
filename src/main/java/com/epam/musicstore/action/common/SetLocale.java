package com.epam.musicstore.action.common;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.Cookie;
import javax.servlet.jsp.jstl.core.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class SetLocale implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(SetLocale.class);
    private static final String LOCALE = "locale";
    private static final int MAX_AGE = 24 * 60 * 60;
    private static final String CHANGED = "{} changed language to {}";


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String language =req.getParameter(LOCALE);
        req.getSession().setAttribute(LOCALE, language);
        Config.set(req.getSession(), Config.FMT_LOCALE, new Locale(language));
        Cookie cookie = new Cookie(LOCALE, language);
        cookie.setMaxAge(MAX_AGE);
        res.addCookie(cookie);
        LOG.info(CHANGED, req.getSession(false).getAttribute(UserConstants.LOGGED_USER), language);
        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
    }
}
