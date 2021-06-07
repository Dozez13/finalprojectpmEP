package com.example.finalprojectpm.web.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@WebFilter(filterName = "LoggerFilter")
public class LoggerFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(LoggerFilter.class);
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
}
