package com.epam.musicstore.action.cart;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearCart implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ClearCart.class);
    private static final String CART_CLEAR_MESSAGE = "cleared cart by - {}";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        req.getSession(false).removeAttribute(PageConstants.CART);
        LOG.info(CART_CLEAR_MESSAGE, req.getSession().getAttribute(UserConstants.LOGGED_USER));
        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
    }
}
