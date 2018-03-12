package com.epam.musicstore.action.user;


import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Address;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import com.epam.musicstore.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUser implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterUser.class);
    private static final String COULDN_T_REGISTER_USER = "Couldn't RegisterUser user";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
       Validate validation = new Validate();
        try {
          if(validation.fullCheck(req) && validation.passwordCheck(req)) {
               return new ActionResult(UserConstants.REGISTER);
           }
            UserUtil userUtil = new UserUtil();
            User user = userUtil.fillUser(req);
            Address adress = userUtil.fillAddress(req);
            UserService userService = new UserService();
            User registeredUser = userService.registerUser(user, adress);
            req.getSession(false).setAttribute(UserConstants.LOGGED_USER, registeredUser);
            req.setAttribute(PageConstants.AFTER_REGISTER_FLAG, TRUE);
        }
        catch (ServiceException e){
            LOG.error(COULDN_T_REGISTER_USER);
            throw new ActionException(COULDN_T_REGISTER_USER, e);
        }
        return new ActionResult(UserConstants.SUCCESS_REGISTERED_PAGE);
    }
}
