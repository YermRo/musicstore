package com.epam.musicstore.action.product;


import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
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
import java.io.File;
import java.io.IOException;

public class AddProduct implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(AddProduct.class);
    private static final String COULDN_T_ADD_PRODUCT = "Couldn't add product";
    private static final String ADDED_PRODUCT = "{} inserted in db and added on central storage by {}";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        try {
            ProductUtil productUtil = new ProductUtil();
            ProductService productService = new ProductService();
            if(productUtil.validateMoney(req)) {
                return new ActionResult(PageConstants.ADD_PRODUCT);
            }
            Product product = productUtil.getFilledProduct(req);
            Image image = new Image();
            Product newProduct = productService.addProduct(product, productUtil.getFilledImage(image, req));
            productService.setProductAsStorageItem(newProduct);
            req.setAttribute(PageConstants.AFTER_PRODUCT_ADD_FLAG, TRUE);
            LOG.info(ADDED_PRODUCT, newProduct, req.getSession(false).getAttribute(UserConstants.LOGGED_USER));
        } catch (ServiceException | IOException | ServletException e) {
            LOG.info(COULDN_T_ADD_PRODUCT, e);
            throw new ActionException(COULDN_T_ADD_PRODUCT, e);
        }
        return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
    }
}
