package com.tellme.backend.exceptions;

public class UserListNotFoundException extends RuntimeException {
    public UserListNotFoundException() {
        super("Users not successfully retrieved.");
    }
}
