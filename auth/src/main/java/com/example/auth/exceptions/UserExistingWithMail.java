package com.example.auth.exceptions;

public class UserExistingWithMail extends RuntimeException{

    public UserExistingWithMail(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistingWithMail(Throwable cause) {
        super(cause);
    }

    public UserExistingWithMail(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserExistingWithMail(String message) {
        super(message);
    }
}
