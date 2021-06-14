package com.example.finalprojectpm.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;


/**
 * Character encoding filter
 * @author Pavlo Manuilenko
 */
public class CharacterEncodingFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(CharacterEncodingFilter.class);
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts");
        this.encoding = filterConfig.getInitParameter("encoding");
        LOGGER.debug("Filter initialization ends");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        //do nothing
        LOGGER.debug("Filter destruction ends");
    }
}
