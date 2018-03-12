package com.epam.musicstore.action.common;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.ProductType;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAddProductPage implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(ShowAddProductPage.class);
    private static final String ERROR = "Couldn't show product-add page";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        List<ProductType> productTypes;
        try{
            ShopService shopService = new ShopService();
            productTypes = shopService.getAllProductTypes();
        }
        catch (ServiceException e){
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
       req.getSession(false).setAttribute(ProductConstants.TYPES, productTypes);
        return new ActionResult(PageConstants.ADD_PRODUCT);
    }
}
