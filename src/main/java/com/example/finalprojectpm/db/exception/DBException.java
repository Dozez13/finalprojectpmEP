package com.example.finalprojectpm.db.exception;

public abstract class DBException extends Exception{

    protected DBException(String message) {
        super (message);


    }
    protected DBException(Throwable cause ) {
        super (cause);

    }

    protected DBException(String message, Throwable cause) {
        super (message, cause);
    }


}