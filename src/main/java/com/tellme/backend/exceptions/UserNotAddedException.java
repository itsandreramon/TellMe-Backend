package com.tellme.backend.exceptions;

public class UserNotAddedException extends RuntimeException {
    public UserNotAddedException(String id) {
        super("User not added: " + id);
    }
}
