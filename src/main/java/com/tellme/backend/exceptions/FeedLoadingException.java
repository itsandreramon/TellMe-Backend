/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.exceptions;

public class FeedLoadingException extends RuntimeException {
  public FeedLoadingException(String id) {
    super("Error loading feed for user: " + id);
  }
}
