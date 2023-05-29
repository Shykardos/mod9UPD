package com.goit;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TimezoneValidateFilter implements Filter {

    private static final String COOKIE_NAME = "lastTimezone";
    private static final String DEFAULT_TIMEZONE = "UTC";

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String timezone = request.getParameter("timezone");

        if (timezone == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(COOKIE_NAME)) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            if (timezone == null) {
                timezone = DEFAULT_TIMEZONE;
            }
        } else {
            Cookie cookie = new Cookie(COOKIE_NAME, timezone);
            response.addCookie(cookie);
        }

        request.setAttribute("timezone", timezone);
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}