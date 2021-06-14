package com.example.finalprojectpm.web.view;


import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * View class
 * @author Pavlo Manuilenko
 */
public class View {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String view;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public String getView() {
        return view;
    }
    public void setView(String view) {
        this.view = view;
    }
    public View(HttpServletRequest request, HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * Navigate to view page
     * @throws IOException if some error occurs within execution
     * @throws ServletException if some error occurs within execution
     */
    public void navigate() throws IOException, ServletException {
        if (view.equals(request.getPathInfo())) {
            request.getRequestDispatcher("/WEB-INF/view/" + FilenameUtils.getName(view) + ".jsp").forward(request, response);
        }
        else {
            response.sendRedirect(view); // We'd like to fire redirect in case of a view change as result of the action (PRG pattern).
        }
    }
}
