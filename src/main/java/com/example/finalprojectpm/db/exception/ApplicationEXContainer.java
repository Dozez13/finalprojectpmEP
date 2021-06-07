package com.example.finalprojectpm.db.exception;

public class ApplicationEXContainer {
    private ApplicationEXContainer(){}
    public static class ApplicationCanChangeException extends ApplicationException{


        private static final long serialVersionUID = 5623020744652767125L;

        public ApplicationCanChangeException(String message) {
            super(message);
        }

        public ApplicationCanChangeException(Throwable cause) {
            super(cause);
        }

        public ApplicationCanChangeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class ApplicationCanNotChangeException extends ApplicationException{


        private static final long serialVersionUID = 8418291280811092712L;

        public ApplicationCanNotChangeException(String message) {
            super(message);
        }

        public ApplicationCanNotChangeException(Throwable cause) {
            super(cause);
        }

        public ApplicationCanNotChangeException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

