/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class UserNotUpdatedException extends RuntimeException {
  public UserNotUpdatedException(String id) {
    super("User not updated: " + id);
  }
}
