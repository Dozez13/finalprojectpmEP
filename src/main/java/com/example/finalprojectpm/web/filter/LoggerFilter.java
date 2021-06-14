package com.example.finalprojectpm.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to logg request info
 * @author Pavlo Manuilenko
 */
@WebFilter(filterName = "LoggerFilter")
public class LoggerFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LoggerFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("Filter initialization starts");
        //do nothing
        LOGGER.debug("Filter initialization ends");
    }

    public String getRequestInfo(ServletRequest request){
        StringBuilder message = new StringBuilder();
        if(request instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            message.append("Request Uri : ").append(httpServletRequest.getRequestURI())
                    .append("Remote Addr : ").append(httpServletRequest.getRemoteAddr())
                    .append("Protocol : ").append(request.getProtocol());
        }
        return message.toString();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String message = getRequestInfo(request);
        LOGGER.info(message);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("Filter destruction starts");
        //do nothing
        LOGGER.debug("Filter destruction ends");
    }
}
