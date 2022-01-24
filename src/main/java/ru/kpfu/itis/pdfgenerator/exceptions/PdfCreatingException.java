package ru.kpfu.itis.pdfgenerator.exceptions;

public class PdfCreatingException extends RuntimeException {

    public PdfCreatingException() {}

    public PdfCreatingException(String message) {
        super(message);
    }

    public PdfCreatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PdfCreatingException(Throwable cause) {
        super(cause);
    }

    public PdfCreatingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
