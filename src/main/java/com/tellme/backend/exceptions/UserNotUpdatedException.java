package com.tellme.backend.exceptions;

public class UserNotUpdatedException extends RuntimeException {
    public UserNotUpdatedException(String id) {
        super("User not updated: " + id);
    }
}
