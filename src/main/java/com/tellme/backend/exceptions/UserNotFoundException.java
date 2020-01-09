/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String id) {
    super("User not found: " + id);
  }
}
