package com.epam.musicstore.action.common;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRegisterPage implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ShowRegisterPage.class);
    private static final String ERROR = "genders list unavailable";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        List<User.Gender> genders;
        try{
            ShopService shopService = new ShopService();
            genders = shopService.getAllGenders();
        }
        catch (ServiceException e){
            LOG.info(ERROR, e);
            throw new ActionException(ERROR, e);
        }
        req.getSession(false).setAttribute(UserConstants.GENDERS, genders);
        return new ActionResult(PageConstants.REGISTER);
    }
}
