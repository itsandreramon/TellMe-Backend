/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class AuthUserNotFoundException extends RuntimeException {
  public AuthUserNotFoundException(String id) {
    super("Auth User not found: " + id);
  }
}
