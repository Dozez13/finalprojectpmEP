package com.example.finalprojectpm.db.exception;

public class MySQLEXContainer {

    private MySQLEXContainer(){}
    public static class MySQLDBNotUniqueException extends DBException{

        private static final long serialVersionUID = -1607368377115493513L;

        public MySQLDBNotUniqueException(String message) {
            super(message);
        }

        public MySQLDBNotUniqueException(Throwable cause) {
            super(cause);
        }

        public MySQLDBNotUniqueException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class MySQLDBLargeDataException extends DBException{

        private static final long serialVersionUID = -958522114940156465L;

        public MySQLDBLargeDataException(String message) {
            super(message);
        }

        public MySQLDBLargeDataException(Throwable cause) {
            super(cause);
        }

        public MySQLDBLargeDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class MySQLDBExecutionException extends DBException{

        private static final long serialVersionUID = 9102526847470741311L;

        public MySQLDBExecutionException(String message) {
            super(message);
        }

        public MySQLDBExecutionException(Throwable cause) {
            super(cause);
        }

        public MySQLDBExecutionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class MySQLDBCarNotFoundException extends DBException{

        private static final long serialVersionUID = 3238280264519746267L;

        public MySQLDBCarNotFoundException(String message) {
            super(message);
        }

        public MySQLDBCarNotFoundException(Throwable cause) {
            super(cause);
        }

        public MySQLDBCarNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class MySQLDBAccountAmountLessThenNul extends DBException{

        private static final long serialVersionUID = 9102526847470741311L;

        public MySQLDBAccountAmountLessThenNul(String message) {
            super(message);
        }

        public MySQLDBAccountAmountLessThenNul(Throwable cause) {
            super(cause);
        }

        public MySQLDBAccountAmountLessThenNul(String message, Throwable cause) {
            super(message, cause);
        }
    }
}