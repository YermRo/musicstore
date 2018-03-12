package com.epam.musicstore.action.product;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import com.epam.musicstore.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserManagePage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowUserManagePage.class);
    private static final String ERROR = "Couldn't show manage users page";
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        PageUtil pageUtil = new PageUtil();
        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        ShopService shopService = new ShopService();
        List<User> users;
        int usersCount;
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        try {
            users = shopService.getAllUsersOnPage(pageInt, pageSizeInt);
            usersCount = shopService.getUsersCount();
        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR);
        }
        int pageCount = pageUtil.getPageCount(usersCount, pageSize);
        req.setAttribute(UserConstants.USERS, users);
        req.setAttribute(PageConstants.PAGES_COUNT, pageCount);
        req.setAttribute(PageConstants.PAGE_SIZE, pageSize);
        req.setAttribute(PageConstants.PAGE, page);
        LOG.info(PageConstants.INFO, page, pageSize, pageCount);
        String path = PageConstants.MANAGE_USERS;
        return new ActionResult(path);
    }
}
