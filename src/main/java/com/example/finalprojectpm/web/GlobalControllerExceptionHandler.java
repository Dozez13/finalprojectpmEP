package com.example.finalprojectpm.web;

import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ApplicationEXContainer.ApplicationSendRegistrationMessageException.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/registration?registrationMessage="+ex.getMessage());
        return mav;
    }
    @ExceptionHandler(ApplicationEXContainer.ApplicationSendOrderMessageException.class)
    public ModelAndView handleErrorOrder(HttpServletRequest req, Exception ex) {
        ModelAndView mav = new ModelAndView("redirect:order?NotAvailable=fail&orderData"+ex.getMessage());
          mav.addObject("NotAvailable","fail");
          mav.addObject("orderData",ex.getMessage());
          return mav;

    }


}
