package com.tellme.backend.exceptions;

public class TellNotFoundException extends RuntimeException {
    public TellNotFoundException(String id) {
        super("Tell not found: " + id);
    }
}
