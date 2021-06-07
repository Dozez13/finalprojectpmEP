package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.web.view.View;

public interface Action {
    void execute(View view) throws Exception;
}