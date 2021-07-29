package com.example.finalprojectpm.web;

import com.example.finalprojectpm.db.exception.ApplicationEXContainer;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ApplicationEXContainer.ApplicationSendRegistrationMessageException.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/registration");
        System.out.println("asdasdasd");
        redirectAttributes.addFlashAttribute("registrationMessage",ex.getMessage());
        return mav;
    }
    @ExceptionHandler(ApplicationEXContainer.ApplicationSendOrderMessageException.class)
    public ModelAndView handleErrorOrder(HttpServletRequest req, Exception ex, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/order");
        redirectAttributes.addFlashAttribute("NotAvailable","fail");
        redirectAttributes.addFlashAttribute("orderData",ex.getMessage());
          return mav;

    }


}
