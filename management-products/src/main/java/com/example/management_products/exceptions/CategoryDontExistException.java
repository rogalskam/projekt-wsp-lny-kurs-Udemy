package com.example.management_products.exceptions;

public class CategoryDontExistException extends RuntimeException {
    public CategoryDontExistException() {
        super();
    }

    public CategoryDontExistException(String message) {
        super(message);
    }

    public CategoryDontExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryDontExistException(Throwable cause) {
        super(cause);
    }

}
