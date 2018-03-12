package com.epam.musicstore.action.cart;



import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.ErrorConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BuyCart implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(BuyCart.class);
    private static final String ERROR_MESSAGE = "{} - doesn't has enough money. Money needed - {}";
    private static final String NOT_ENOUGH = "notEnough";
    private static final String ERROR_PLACING = "Couldn't place order";
    private static final String BOUGHT_ORDER = "{} has been bought by - {}";
    private static final String SUCCESS_BUY = "successBuy";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        Order order = (Order) req.getSession().getAttribute(PageConstants.CART);
        User loggedUser = (User) req.getSession().getAttribute(UserConstants.LOGGED_USER);
        order.setUser(loggedUser);
        if (loggedUser.getCash().isLessThan(order.getPrice())) {
            req.setAttribute(ErrorConstants.BALANCE_ERROR, TRUE);
            return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
        }
        try {
            ShopService shopService = new ShopService();
            User user = shopService.buyCart(order);
            req.getSession().setAttribute(UserConstants.LOGGED_USER, user);
            req.getSession(false).removeAttribute(PageConstants.CART);
            req.setAttribute(SUCCESS_BUY, TRUE);
            LOG.info(BOUGHT_ORDER, order, loggedUser);
            return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
        } catch (ServiceException e) {
            LOG.info(ERROR_PLACING, e);
            throw new ActionException(ERROR_PLACING, e);
        }
    }
}
