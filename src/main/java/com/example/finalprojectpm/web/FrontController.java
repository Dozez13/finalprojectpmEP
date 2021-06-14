package com.example.finalprojectpm.web;


import com.example.finalprojectpm.web.command.Action;
import com.example.finalprojectpm.web.command.ActionFactory;
import com.example.finalprojectpm.web.view.View;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * Main controller that implements Front Controller design pattern
 * @author Pavlo Manuilenko
 */
@WebServlet(name = "FrontController", value = "/pages/*")
@MultipartConfig
public class FrontController extends HttpServlet {

    /**
     * Method for processing requests
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ActionFactory actionFactory = (ActionFactory) getServletContext().getAttribute("actionFactory");
            View view = new View(req, resp);
            Action action = actionFactory.getAction(req);
            action.execute(view);
            view.navigate();
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/view/error.jsp?errorMessage="+e.getMessage()).forward(req,resp);
        }
    }
}
