package com.epam.musicstore.web.Filter;

import com.epam.musicstore.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    private static final String WELCOME = "/welcome";
    private static final String PRODUCT = "/product";
    private static final String CATALOG = "/catalog";
    private static final String REGISTER = "/register";
    private static final String LOGIN = "/login";
    private static final String LOCALE = "/locale";
    private static final String LOGGED_USER = "loggedUser";
    private static final String CART = "/cart";
    private static final String BUY = "buy";
    private static final String DO_HOME = "/do/home";
    private static final String NOT_LOGGED_USER_REDIRECTED_TO_LOGIN_PAGE = "not logged user redirected to login page";
    private static final String ALREADY_LOGGED_IN = "already logged in";
    private static final String MANAGE = "/manage";
    private static final String REFILL = "/refill";
    private static final String ADD = "/add";
    private static final String HOME = "/home";
    private static final String ACCESS_DENIED = "Access denied";
    private static final String NOT_LOGGED_USER_REDIRECTED_TO_NOME_PAGE = "Not logged user redirected to nome page";
    private static final String LOG_IN = "/login";
    private List<String> guestAccessList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestAccessList = new ArrayList<>();
        guestAccessList.add(HOME);
        guestAccessList.add(PRODUCT);
        guestAccessList.add(CATALOG);
        guestAccessList.add(REGISTER);
        guestAccessList.add(LOCALE);
        guestAccessList.add(LOG_IN);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        User user = (User) request.getSession(false).getAttribute(LOGGED_USER);
        String pathInfo = request.getPathInfo();
        if (user == null) {
            if (!guestAccessList.contains(pathInfo) && !pathInfo.startsWith(CART) || pathInfo.endsWith(BUY)) {
                response.sendRedirect(request.getContextPath() + DO_HOME);
                LOG.info(NOT_LOGGED_USER_REDIRECTED_TO_NOME_PAGE);
                return;
            }
        } else if (pathInfo.startsWith(LOGIN)) {
            response.sendError(403, ALREADY_LOGGED_IN);
            return;
        } else if (user.getRole().equals("user")) {
            if (pathInfo.startsWith(MANAGE) || pathInfo.startsWith(REFILL)) {
                response.sendError(403, ACCESS_DENIED);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
