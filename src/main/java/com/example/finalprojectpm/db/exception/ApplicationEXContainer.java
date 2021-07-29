package com.example.finalprojectpm.db.exception;

public class ApplicationEXContainer {
    private ApplicationEXContainer(){}
    public static class ApplicationSendRegistrationMessageException extends ApplicationException{
        public ApplicationSendRegistrationMessageException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class ApplicationSendOrderMessageException extends ApplicationException{
        public ApplicationSendOrderMessageException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ApplicationNotEnoughException extends ApplicationException{
        public ApplicationNotEnoughException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class ApplicationCantRecoverException extends ApplicationException{


        private static final long serialVersionUID = 8418291280811092712L;

        public ApplicationCantRecoverException(String message) {
            super(message);
        }

        public ApplicationCantRecoverException(Throwable cause) {
            super(cause);
        }

        public ApplicationCantRecoverException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

