package com.epam.musicstore.action.user;

import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.Address;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login implements Action {

    private static final Logger LOG = LoggerFactory.getLogger(Action.class);
    private static final String SERVICE_ERROR = "Couldn't login";
    private static final String LOGIN_ERROR = "loginError";
    private static final String LOGGED = "{} logged";
    private static final String INPUT_ERROR = "wrong email {} or password {}";
    private static final String INVALID_DATA = "Invalid login or password";
    private static final String TRUE = "true";

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {
        String email = req.getParameter(UserConstants.EMAIL);
        String password = req.getParameter(UserConstants.PASS_WORD);
        String md5HexPassword = DigestUtils.md5Hex(password);
        User user;
        Address address;
        try{
            UserService userService = new UserService();
            user = userService.logIn(email, md5HexPassword);
            if(user != null){
                req.getSession().setAttribute(UserConstants.LOGGED_USER, user);
                req.getSession().setAttribute(UserConstants.PASS_WORD, password);
                LOG.info(LOGGED, user);
                return new ActionResult(PageConstants.HOME, true);
            }
            else {
                LOG.info(INPUT_ERROR, email, password);
                req.setAttribute(LOGIN_ERROR, TRUE);
                return new ActionResult(PageConstants.SUCCESS_REGISTERED_PAGE);
            }
        }
        catch (ServiceException e){
            LOG.info(SERVICE_ERROR, e);
            throw new ActionException(SERVICE_ERROR, e);
        }
    }
}
