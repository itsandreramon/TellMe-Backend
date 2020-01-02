package com.tellme.backend.exceptions;

public class UserNotDeletedException extends RuntimeException {
    public UserNotDeletedException(String id) {
        super("User not deleted: " + id);
    }
}
