package com.tellme.backend.exceptions;

public class AuthUserNotFoundException extends RuntimeException {
    public AuthUserNotFoundException(String id) {
        super("Auth User not found: " + id);
    }
}
