package com.epam.musicstore.action.product;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.Image;
import com.epam.musicstore.entity.Product;
import com.epam.musicstore.service.ProductService;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.util.ProductUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditProduct implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(EditProduct.class);
    private static final String INVALID_MONEY_FORMAT = "Invalid money format - {}";
    private static final String COULDN_T_EDIT_PRODUCT = "Couldn't edit product";
    private static final String AFTER_PRODUCT_EDIT_FLAG = "afterProductEditFlag";
    private static final String TRUE = "true";
    private ProductUtil productUtil = new ProductUtil();
    private String id;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        try {
            ProductService productService = new ProductService();
            if (checkPrice(req)) {
                return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
            }
            productService.updateProduct(getFilledProduct(req));
            Image image = productService.getProductPreviewImage(id);
            image = productUtil.getFilledImage(image, req);
            if (!productUtil.validateImage(req)) {
                productService.updateProductImage(image);
            }
            req.setAttribute(AFTER_PRODUCT_EDIT_FLAG, TRUE);
        } catch (IOException | ServiceException | ServletException e) {
            LOG.info(COULDN_T_EDIT_PRODUCT, e);
            throw new ActionException(COULDN_T_EDIT_PRODUCT, e);
        }
        return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);

    }

    private Product getFilledProduct(HttpServletRequest req){
        id = req.getParameter(ProductConstants.ID);
        Product product = productUtil.getFilledProduct(req);
        product.setId(Integer.valueOf(id));
        return product;
    }
    private boolean checkPrice(HttpServletRequest req) throws ActionException {
        Validate validation = new Validate();
        String price = req.getParameter(ProductConstants.PRICE);
        if (validation.checkMoney(req, price)) {
            LOG.info(INVALID_MONEY_FORMAT, price);
            return true;
        }
        return false;
    }
}
