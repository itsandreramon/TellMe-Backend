/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.services;

import com.tellme.backend.model.Tell;
import com.tellme.backend.repositories.TellRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InboxService {

  private final TellRepository tellRepository;

  public InboxService(TellRepository tellRepository) {
    this.tellRepository = tellRepository;
  }

  public List<Tell> getInboxByUser(String uid) {
    return tellRepository.getInboxByUser(uid);
  }
}
