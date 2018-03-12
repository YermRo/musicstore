package com.epam.musicstore.action.product;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.Product;
import com.epam.musicstore.entity.ProductType;
import com.epam.musicstore.service.ProductService;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowEditProductPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowEditProductPage.class);
    private static final String COULDN_T_SHOW_EDIT_PRODUCT_PAGE = "Couldn't show edit product page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        ShopService shopService = new ShopService();
        ProductService productService = new ProductService();

        try {
            Product product = productService.getFilledProduct(req.getParameter(ProductConstants.ID));
            req.setAttribute(ProductConstants.PRODUCT, product);
            List<ProductType> productTypes = shopService.getAllProductTypes();
            req.setAttribute(ProductConstants.TYPES, productTypes);
            String imagePath = product.getImages().get(0).getPath() + "/" +
                    product.getImages().get(0).getName();
            req.setAttribute("imagePath", imagePath);
            return new ActionResult(PageConstants.EDIT_PRODUCT);
        } catch (ServiceException e) {
            LOG.info(COULDN_T_SHOW_EDIT_PRODUCT_PAGE, e);
            throw new ActionException(COULDN_T_SHOW_EDIT_PRODUCT_PAGE, e);
        }
    }
}