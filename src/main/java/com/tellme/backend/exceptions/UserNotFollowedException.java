package com.tellme.backend.exceptions;

public class UserNotFollowedException extends RuntimeException {
    public UserNotFollowedException(String uid) {
        super("User not followed: " + uid);
    }
}
