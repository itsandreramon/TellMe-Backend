/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class UserNotFollowedException extends RuntimeException {
  public UserNotFollowedException(String uid) {
    super("User not followed: " + uid);
  }
}
