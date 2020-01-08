/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class UserListNotFoundException extends RuntimeException {
  public UserListNotFoundException() {
    super("Users not successfully retrieved.");
  }
}
