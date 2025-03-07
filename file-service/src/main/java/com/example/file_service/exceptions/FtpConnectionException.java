package com.example.file_service.exceptions;

public class FtpConnectionException extends RuntimeException {
    public FtpConnectionException(String message) {
        super(message);
    }

    public FtpConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpConnectionException(Throwable cause) {
        super(cause);
    }
}
