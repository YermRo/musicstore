package com.epam.musicstore.action.order;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrder implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteOrder.class);
    private static final String ERROR_DELETE = "Couldn't delete order by id";
    private static final String ORDER_DELETED = "order - {} has been deleted";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String id = req.getParameter(UserConstants.ID);
        try {
            ShopService shopService = new ShopService();
            shopService.deleteOrderById(id);
        } catch (ServiceException e) {
            LOG.info(ERROR_DELETE, e);
            throw new ActionException(ERROR_DELETE);
        }
        LOG.info(ORDER_DELETED, id);
        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
    }
}
