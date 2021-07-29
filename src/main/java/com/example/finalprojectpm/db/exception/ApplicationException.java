package com.example.finalprojectpm.db.exception;

public abstract class ApplicationException extends RuntimeException{
    protected ApplicationException(String message) {
        super (message);


    }
    protected ApplicationException(Throwable cause ) {
        super (cause);

    }

    protected ApplicationException(String message, Throwable cause) {
        super (message, cause);
    }
}