package com.epam.musicstore.action.order;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import com.epam.musicstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowOrderPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowOrderPage.class);
    private static final String ERROR = "Couldn't show order page";
    private static final String ORDER_ID = "orderId";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String orderId = req.getParameter(ORDER_ID);
        Order order;
        try {
            ShopService shopService = new ShopService();
            order = shopService.getOrder(Integer.parseInt(orderId));
            UserService userService = new UserService();
            User user = userService.getUserByID(order.getUser().getId());
            req.setAttribute(PageConstants.ORDER, order);
            req.setAttribute(UserConstants.USER, user);
            return new ActionResult(PageConstants.ORDER);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR);
        }

    }
}
