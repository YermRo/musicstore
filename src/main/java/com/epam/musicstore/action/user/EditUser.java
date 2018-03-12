package com.epam.musicstore.action.user;


import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionResult;
import com.epam.musicstore.action.common.Validate;
import com.epam.musicstore.constants.PageConstants;
import com.epam.musicstore.constants.UserConstants;
import com.epam.musicstore.entity.User;
import com.epam.musicstore.service.ServiceException;
import com.epam.musicstore.service.UserService;
import com.epam.musicstore.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser implements Action {
    private static final String UPDATED_ERROR = "Could not update profile";
    private static final String TRUE = "true";
    private static final Logger LOG = LoggerFactory.getLogger(EditUser.class);

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse res) throws ActionException {

        try {
            UserService userService = new UserService();
            User user;
            UserUtil userUtil = new UserUtil();
            Validate validation = new Validate();
            user = userService.getUserByID(Integer.valueOf(req.getParameter(UserConstants.USER_ID)));
            if(validation.validateUser(req)){
                return new ActionResult(PageConstants.USER_DATA);
            }
            userUtil.fillUserExceptPassword(req, user);
            String path = PageConstants.SUCCESS_REGISTERED_PAGE;
            req.setAttribute(PageConstants.AFTER_USER_DATA_CHANGE_FLAG, TRUE);
            req.getSession(false).setAttribute(UserConstants.LOGGED_USER, user);
            return new ActionResult(path);

        } catch (ServiceException e) {
            LOG.info(UPDATED_ERROR, e);
            throw new ActionException(UPDATED_ERROR, e);
        }
    }
}
