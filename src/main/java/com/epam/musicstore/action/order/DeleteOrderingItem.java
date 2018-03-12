package com.epam.musicstore.action.order;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderingItem implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteOrderingItem.class);
    private static final String ITEM_DELETED = "item - {} deleted from cart";
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        Order order = (Order) req.getSession().getAttribute(PageConstants.CART);
        String rowNumber = req.getParameter(OrderConstants.ITEM);
        int rowNumberInt = Integer.parseInt(rowNumber);
        LOG.info(ITEM_DELETED, order.getOrderingItems().get(rowNumberInt));
        order.getOrderingItems().remove(rowNumberInt);
        req.getSession().setAttribute(PageConstants.CART, order);
        return new ActionResult(PageConstants.CART);
    }

}
