package com.epam.rd.controllers.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LogFilter implements Filter {
    private FilterConfig config = null;
    protected static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    public void destroy() {
        config = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String filterName = config.getFilterName();
        String servletPath = "Servlet path: " + httpRequest.getServletPath();

        logger.info(filterName + " | " + servletPath + " | before request");
        chain.doFilter(httpRequest, httpResponse);
        logger.info(filterName + " | " + servletPath + " | after request");
    }

}