package com.epam.musicstore.action.product;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.ProductConstants;
import com.epam.musicstore.entity.StorageItem;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowProductPage implements Action {


        private static final Logger LOG = LoggerFactory.getLogger(ShowProductPage.class);
        private static final String ERROR = "Could't show product page action";
    private static final String ITEM_AMOUNT_ERROR = "itemAmountError";
    private static final String TRUE = "true";

    @Override
        public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
            StorageItem item;
            List<StorageItem> allItems = new ArrayList<>();
            List<StorageItem> items = new ArrayList<>();
            try {
                ShopService shopService = new ShopService();
                item = shopService.getStorageItemByID(Integer.valueOf(req.getParameter(ProductConstants.ID)));
                allItems = shopService.getAllStorageItems();
                Random random = new Random();
                if (item.getAmount() <= 0){
                    req.setAttribute(ITEM_AMOUNT_ERROR, TRUE);
                }
                for (int i = 1; i < 5; i++){
                        items.add(allItems.get(random.nextInt(allItems.size()-1)));
                }
            } catch (ServiceException e) {
                LOG.info(ERROR, e);
                throw new ActionException(ERROR);
            }
            req.setAttribute(ProductConstants.ITEM,  item);
            req.setAttribute(ProductConstants.ITEMS,  items);
            return new ActionResult(PageConstants.PRODUCT);
        }
}
