package com.epam.musicstore.web.servlet;
import com.epam.musicstore.action.Action;
import com.epam.musicstore.action.ActionException;
import com.epam.musicstore.action.ActionFactory;
import com.epam.musicstore.action.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
@MultipartConfig(maxFileSize = 104_857_600)
public class MusicStoreServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MusicStoreServlet.class);
    private static final String CANNOT_EXECUTE_ACTION = "Cannot execute action";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String NO_CACHE_NO_STORE_MUST_REVALIDATE = "no-cache, no-store, must-revalidate";
    private static final String NO_CACHE = "no-cache";
    private static final String PRAGMA = "Pragma";
    private static final String EXPIRES = "Expires";
    private static final String NOT_FOUND = "Not found";
    private static final String DO = "/do/";
    private static final String HTTP = "http://";
    private static final String WEB_INF_JSP = "/WEB-INF/jsp/";
    private static final String JSP = ".jsp";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String actionName = req.getMethod() + req.getPathInfo();
        Action action = ActionFactory.getAction(actionName);
        if (action == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, NOT_FOUND);
            return;
        }
        ActionResult result = null;
        try {
            result = action.execute(req, resp);
            if (result == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, NOT_FOUND);
                return;
            }
        } catch (ActionException e) {
            LOG.error(CANNOT_EXECUTE_ACTION, e);
            throw new ServletException(CANNOT_EXECUTE_ACTION, e);
        }
        resp.setHeader(CACHE_CONTROL, NO_CACHE_NO_STORE_MUST_REVALIDATE);
        resp.setHeader(PRAGMA, NO_CACHE);
        resp.setDateHeader(EXPIRES, 0);
        doForwardOrRedirect(result, req, resp);
  }

    private void doForwardOrRedirect(ActionResult result, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        if (result.isRedirect()) {
            String location = req.getContextPath() + DO + result.getView();
            if (result.getView().startsWith(HTTP)) {
                location = result.getView();

           }
            resp.sendRedirect(location);
        } else {
            String path = WEB_INF_JSP + result.getView() + JSP;
            req.getRequestDispatcher(path).forward(req, resp);

        }

    }


}
