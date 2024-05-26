package com.example.licenseebe.helper;

public class CustomEntityNotFoundException extends Exception {
    private final String entity;

    public CustomEntityNotFoundException(Class<?> cause) {
        super(cause.getSimpleName());
        this.entity = cause.getSimpleName();
    }

    public CustomEntityNotFoundException(String className) {
        this.entity = className;
    }


    public String getErrorMessage() {
        return entity.toUpperCase() + "_NOT_FOUND";
    }
}
