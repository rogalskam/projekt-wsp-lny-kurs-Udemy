package com.example.auth.entity;

public enum Code {
    SUCCESS("Operation end success");


    public final String label;
    private Code(String label) {
        this.label = label;
    }
}
