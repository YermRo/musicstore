package com.epam.musicstore.action.order;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOrderStatus implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditOrderStatus.class);
    private static final String EDIT_ERROR = "Couldn't edit order status";
    private static final String UPDATED = "{} order has been upadted - status: {}";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        ShopService shopService = new ShopService();
        try {
            String statusId = req.getParameter(OrderConstants.STATUS_ID);
            String orderId = req.getParameter(OrderConstants.ORDER_ID);
            shopService.updateOrderStatus(orderId, statusId);
            LOG.info(UPDATED, orderId, statusId);
        } catch (ServiceException e) {
            LOG.info(EDIT_ERROR, e);
            throw new ActionException(EDIT_ERROR, e);
        }
        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
    }
}
