package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.web.view.View;

import javax.servlet.http.HttpServletRequest;

public class AddMoneyAction implements Action{
    @Override
    public void execute(View view) throws Exception {
        HttpServletRequest request = view.getRequest();
        view.setView(request.getPathInfo());
    }
}
