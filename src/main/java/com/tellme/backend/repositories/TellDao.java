/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.repositories;

import com.tellme.backend.model.Tell;
import java.util.List;
import java.util.Optional;

public interface TellDao {
  Optional<Tell> findTellById(String id);

  Boolean deleteTellById(String id);

  Optional<Boolean> insertTell(Tell tell);

  Boolean updateTell(Tell tell);

  Boolean removeReply(String id);

  List<Tell> getAllTells();

  List<Tell> getInboxByUser(String uid);

  List<Tell> getAllTellsBySender(String uid);

  List<Tell> getTellFeedByUserUid(String uid, List<String> follows);
}
