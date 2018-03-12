package com.epam.musicstore.action.order;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.OrderConstants;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Order;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import com.epam.musicstore.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowMyOrdersPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowMyOrdersPage.class);
    private static final String ERROR = "Couldn't get orders";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        PageUtil<Order> pageUtil = new PageUtil<>();
        User user = (User) req.getSession().getAttribute(UserConstants.LOGGED_USER);
        List<Order> orders;
        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        List<Order> ordersOnPage;
        try {
            UserService userService = new UserService();
            orders = userService.getUserOrders(user.getId());
            ordersOnPage = pageUtil.getEntitiesOnPage(orders, pageInt, pageSizeInt);
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(orders.size(), pageSize);
        req.setAttribute(PageConstants.PAGE, page);
        req.setAttribute(PageConstants.PAGE_SIZE, pageSize);
        req.setAttribute(PageConstants.PAGES_COUNT, pageCount);
        req.setAttribute(OrderConstants.ORDERS, ordersOnPage);
        LOG.info(PageConstants.INFO, page, pageSize, pageCount);
        String path = PageConstants.MY_ORDERS;
        return new ActionResult(path);
    }
}
