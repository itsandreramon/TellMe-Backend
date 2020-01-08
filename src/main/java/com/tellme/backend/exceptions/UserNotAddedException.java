/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class UserNotAddedException extends RuntimeException {
  public UserNotAddedException(String id) {
    super("User not added: " + id);
  }
}
