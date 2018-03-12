package com.epam.musicstore.action.product;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.entity.StorageItem;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import com.epam.musicstore.util.PageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowCatalog implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowCatalog.class);
    private static final String ERROR = "Couldn't show catalog page";
    private static final String ITEM_AMOUNT_ERROR = "itemAmountError";
    private static final String ERROR_MAP = "errorMap";


    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        List<StorageItem> itemsByType = new ArrayList<>();
        PageUtil<StorageItem> pageUtil = new PageUtil<>();
        Map<Integer, String> errorMap = new HashMap<>();

        String page = pageUtil.getPage(req);
        String pageSize = pageUtil.getPageSize(req);
        String type = req.getParameter(PageConstants.TYPE);
        if (type == null) {
            type = (String) req.getSession().getAttribute(PageConstants.TYPE);
        }
        int pageInt = Integer.parseInt(page);
        int pageSizeInt = Integer.parseInt(pageSize);
        List<StorageItem> items;
        try {
            ShopService shopService = new ShopService();
            items = shopService.getAllStorageItems();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getProduct().getType().getId().toString().equals(type) &&
                        !items.get(i).isDeleted()) {
                    itemsByType.add(items.get(i));
                }
            }



        } catch (ServiceException e) {
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        int pageCount = pageUtil.getPageCount(itemsByType.size(), pageSize);
        List<StorageItem> itemsOnPage = pageUtil.getEntitiesOnPage(itemsByType, pageInt, pageSizeInt);

        for (int i = 0; i < itemsOnPage.size(); i++) {
            Validate validate = new Validate();
            if (validate.checkStorageItemAmount(itemsOnPage.get(i).getAmount())) {
                errorMap.put(i, ITEM_AMOUNT_ERROR);
            }
        }

        req.setAttribute(PageConstants.PAGES_COUNT, pageCount);
        req.setAttribute(PageConstants.PAGE, page);
        req.setAttribute(PageConstants.PAGE_SIZE, pageSize);
        req.setAttribute(PageConstants.STORAGE_ITEMS, itemsOnPage);
        req.setAttribute(ERROR_MAP, errorMap);
        if ((type != null)) {
            req.getSession().setAttribute(PageConstants.TYPE, type);
        }

        LOG.info(PageConstants.INFO, page, pageSize, pageCount);
        return new ActionResult(PageConstants.CATALOG_PAGE);


    }
}
