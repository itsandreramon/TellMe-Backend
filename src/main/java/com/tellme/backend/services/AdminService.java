/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
  @PreAuthorize("hasRole(@roles.ADMIN)")
  public boolean ensureAdmin() {
    return true;
  }
}
