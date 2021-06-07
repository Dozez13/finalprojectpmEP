package com.example.finalprojectpm.db.exception;

public abstract class ApplicationException extends Exception{
    public ApplicationException(String message) {
        super (message);


    }
    public ApplicationException(Throwable cause ) {
        super (cause);

    }

    public ApplicationException(String message, Throwable cause) {
        super (message, cause);
    }
}