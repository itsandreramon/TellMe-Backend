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
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TellService {

  private final TellRepository tellRepository;

  public TellService(TellRepository tellRepository) {
    this.tellRepository = tellRepository;
  }

  public Optional<Tell> findTellById(String id) {
    return tellRepository.findTellById(id);
  }

  public Boolean deleteTellById(String id) {
    return tellRepository.deleteTellById(id);
  }

  public Boolean updateTell(Tell tell) {
    return tellRepository.updateTell(tell);
  }

  public Boolean removeReply(String id) {
    return tellRepository.removeReply(id);
  }

  public Optional<Boolean> insertTell(Tell tell) {
    return tellRepository.insertTell(tell);
  }

  public List<Tell> getAllTells() {
    return tellRepository.getAllTells();
  }

  public List<Tell> getAllTellsBySender(String uid) {
    return tellRepository.getAllTellsBySender(uid);
  }
}