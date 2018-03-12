package com.epam.musicstore.action.cart;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.ErrorConstants;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.OrderingItem;
import com.epam.musicstore.entity.Product;
import com.epam.musicstore.service.ProductService;
import com.epam.musicstore.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToCart implements Action {
    private static final String ERROR_ADD = "Couldn't add product to cart";
    private static final String INVALID_AMOUNT = "Invalid product amount format - {}";
    private static final String AMOUNT_INCREASED = "Product amount in cart increased by - {}";
    private static final String PRODUCT_ADDED = "product - {} added in cart. Amount - {}";
    private static final Logger LOG = LoggerFactory.getLogger(AddToCart.class);
    private String amount;
    private Order cart;

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

     if (checkAmount(req)) {
        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
    }
    cart = getCart(req);
    String productId = req.getParameter(ProductConstants.PRODUCT);
    Integer amountInt = Integer.parseInt(amount);
        if(!increaseAmountIfAlreadyExists(amountInt ,productId, req)) {
        setOrderingItem(req, amountInt ,productId);
    }

        return new ActionResult(req.getHeader(PageConstants.REFERER_PAGE), true);
}

    private boolean checkAmount(HttpServletRequest req) throws ActionException {
        amount = req.getParameter(OrderConstants.AMOUNT);
        Validate validation = new Validate();
        if (validation.checkAmount(req, amount)) {
            req.setAttribute(ErrorConstants.ERROR_AMOUNT, ErrorConstants.TRUE);
            LOG.info(INVALID_AMOUNT, amount);
            return true;
        }
        return false;
    }

    private void setOrderingItem(HttpServletRequest req, Integer amountInt, String productId) throws ActionException {
        try {
            OrderingItem orderingItem = new OrderingItem();
            orderingItem.setAmount(amountInt);
            ProductService productService = new ProductService();
            Product product = productService.getProductByID(Integer.valueOf(productId));
            orderingItem.setProduct(product);
            cart.addProduct(orderingItem);
            req.getSession().setAttribute(PageConstants.CART, cart);
            LOG.info(PRODUCT_ADDED, product, amount);
        } catch (ServiceException e) {
            LOG.info(ERROR_ADD, e);
            throw new ActionException(ERROR_ADD, e);
        }
    }

    private boolean increaseAmountIfAlreadyExists(Integer amountInt, String productId, HttpServletRequest req){
        for (OrderingItem orderingItem : cart.getOrderingItems()) {                   //if already in cart
            if (orderingItem.getProduct().getId() == Integer.parseInt(productId)) {
                orderingItem.setAmount(orderingItem.getAmount() + amountInt);
                req.getSession().setAttribute(PageConstants.CART, cart);
                LOG.info(AMOUNT_INCREASED, amount);
                return true;
            }
        }
        return false;
    }

    private Order getCart(HttpServletRequest req){
        Order cart = (Order) req.getSession(false).getAttribute(PageConstants.CART);
        if (cart == null) {
            cart = new Order();
        }
        return cart;
    }
}
