/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.repository;

import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TellRepository extends ReactiveCrudRepository<Tell, String> {

    /**
     * Returns all {@link Tell}s that contain a given receiver uid.
     *
     * @param receiverUid
     * @return
     */
    Flux<Tell> findByReceiverUid(String receiverUid);

    /**
     * Returns all {@link Tell}s that contain a given sender uid.
     *
     * @param senderUid
     * @return
     */
    Flux<Tell> findBySenderUid(String senderUid);
}
