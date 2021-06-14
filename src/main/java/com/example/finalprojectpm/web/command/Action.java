package com.example.finalprojectpm.web.command;

import com.example.finalprojectpm.web.view.View;

/**
 *  Main interface for the Command/Action pattern implementation.
 *  @author Pavlo Manuilenko
 */
public interface Action {

    /**
     * Execution method for all actions
     * @param view View that contains request and response to operate with
     * @throws Exception if application exception is thrown
     */
    void execute(View view) throws Exception;
}