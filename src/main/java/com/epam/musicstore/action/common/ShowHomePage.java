package com.epam.musicstore.action.common;
import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.entity.ProductType;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import com.epam.musicstore.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
public class ShowHomePage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowHomePage.class);
    private static final String ERROR = "Couldn't fill content at home page";
    private static final String PRODUCT_TYPES = "productTypes";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        PageUtil pageUtil = new PageUtil();
        ShopService shopService = new ShopService();
        List<ProductType> productTypes;

        try {
            productTypes = shopService.getAllProductTypes();
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR);
        }
        req.getSession().setAttribute(PRODUCT_TYPES, productTypes);
        return new ActionResult(PageConstants.HOME);
    }
}
