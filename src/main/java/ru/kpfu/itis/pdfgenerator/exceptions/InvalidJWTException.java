package ru.kpfu.itis.pdfgenerator.exceptions;

public class InvalidJWTException extends RuntimeException {

    public InvalidJWTException() {}

    public InvalidJWTException(String message) {
        super(message);
    }

    public InvalidJWTException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidJWTException(Throwable cause) {
        super(cause);
    }

    public InvalidJWTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
