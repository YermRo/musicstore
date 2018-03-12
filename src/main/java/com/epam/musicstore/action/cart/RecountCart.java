package com.epam.musicstore.action.cart;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.ErrorConstants;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.OrderingItem;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class RecountCart implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(RecountCart.class);
    private static final String INVALID_PRODUCT_AMOUNT_FORMAT = "Invalid product amount format - {}";
    private static final String AMOUNT_SET_TO = "{} amount set to {}";
    private static final String ITEM_AMOUNT = "itemAmount";
    private static final String ITEM_AMOUNT_ERROR = "itemAmountError";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        ShopService shopService = new ShopService();
        Order cart = (Order) req.getSession(false).getAttribute(PageConstants.CART);
        List<OrderingItem> orderItems = cart.getOrderingItems();
        Map<Integer, String> errorMap = new HashMap<>();
        for (int i = 0; i < orderItems.size(); i++) {
            String amount = req.getParameter(OrderConstants.ITEM + i);
            Validate validation = new Validate();
            if (validation.checkAmount(req, amount)) {
                errorMap.put(i, ErrorConstants.TRUE);
                LOG.info(INVALID_PRODUCT_AMOUNT_FORMAT, amount);
            } else {
                int productId = orderItems.get(i).getProduct().getId();
                try {
                int storageItemAmount = shopService.getStorageItemByProductId(productId).getAmount();

                    if (validation.checkStorageItemAmount(Integer.parseInt(amount), storageItemAmount)){
                        orderItems.get(i).setAmount(storageItemAmount);
                        errorMap.put(i, ITEM_AMOUNT_ERROR);
                        LOG.info(AMOUNT_SET_TO, orderItems.get(i), amount);
                    }
                    else {
                        orderItems.get(i).setAmount(Integer.parseInt(amount));
                        LOG.info(AMOUNT_SET_TO, orderItems.get(i), amount);
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        req.setAttribute(ErrorConstants.ERROR_MAP, errorMap);
        req.getSession().setAttribute(PageConstants.CART, cart);
        return new ActionResult(PageConstants.CART);
    }
}
