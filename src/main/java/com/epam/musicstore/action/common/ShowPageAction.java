


package com.epam.musicstore.action.common;



import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class ShowPageAction implements Action {
    private ActionResult result;
    public ShowPageAction(String page) {
        result = new ActionResult(page);
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return result;
    }
}