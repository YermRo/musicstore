package com.epam.musicstore.action.product;


import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProduct implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteProduct.class);
    private static final String ERROR_DELETE = "Couldn't delete product by id";
    private static final String DELETED_PRODUCT = "product - {} has been deleted";
    private static final String AFTER_PRODUCT_DELETE_FLAG = "afterProductDeleteFlag";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String id = req.getParameter(ProductConstants.ID);
        try {
            ShopService shopService = new ShopService();
            shopService.deleteProductById(id);
            shopService.deleteStorageItemByProductId(id);
        } catch (ServiceException e) {
            LOG.info(ERROR_DELETE, e);
            throw new ActionException(ERROR_DELETE);
        }
        LOG.info(DELETED_PRODUCT, id);
        req.setAttribute(AFTER_PRODUCT_DELETE_FLAG, TRUE);
        return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
    }
}
