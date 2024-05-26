package com.example.licenseebe.helper;

public class CustomConflictException extends Exception{

    private final String conflict;

    public CustomConflictException(Conflict conflict) {
        super(conflict.toString());
        this.conflict = conflict.toString();
    }

    public String getErrorMessage() {
        return conflict;
    }
}
