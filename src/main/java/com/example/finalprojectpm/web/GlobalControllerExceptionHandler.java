package com.example.finalprojectpm.web;

import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralErrors(HttpServletRequest req, Exception ex) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
    @ExceptionHandler(ApplicationEXContainer.ApplicationSendRegistrationMessageException.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("registrationMessage", ex.getMessage());
        mav.setViewName("registration");
        return mav;
    }
    @ExceptionHandler(ApplicationEXContainer.ApplicationSendOrderMessageException.class)
    public ModelAndView handleErrorOrder(HttpServletRequest req, Exception ex) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
    @ExceptionHandler(ApplicationEXContainer.ApplicationNotEnoughException.class)
    public ModelAndView handleErrorProfileAddMoney(HttpServletRequest req, Exception ex) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }

}
