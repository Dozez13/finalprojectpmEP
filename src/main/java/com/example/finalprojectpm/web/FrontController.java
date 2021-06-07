package com.example.finalprojectpm.web;

import com.example.finalprojectpm.web.command.Action;
import com.example.finalprojectpm.web.command.ActionFactory;
import com.example.finalprojectpm.web.view.View;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;


@WebServlet(name = "FrontController", value = "/pages/*")
public class FrontController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String error = (String)context.getAttribute("Error");
        if(error!=null){
            req.getRequestDispatcher("/WEB-INF/view/error.jsp?errorMessage="+error);
        }
        try {
            View view = new View(req, resp);
            Action action = ActionFactory.getInstance().getAction(req);
            action.execute(view);
            view.navigate();
        } catch (Exception e) {
            req.getRequestDispatcher("/WEB-INF/view/error.jsp?errorMessage="+error);
        }
    }
}
