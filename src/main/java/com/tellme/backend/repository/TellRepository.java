/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.repository;

import com.tellme.backend.model.Tell;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TellRepository extends ReactiveCrudRepository<Tell, String> {

	/**
	 * Returns all {@link Tell}s that belong to a given receiver uid.
	 *
	 * @param receiverUid
	 * @return
	 */
	Flux<Tell> findByReceiverUid(String receiverUid);

	/**
	 * Returns all {@link Tell}s that belong to a given sender uid.
	 *
	 * @param senderUid
	 * @return
	 */
	Flux<Tell> findBySenderUid(String senderUid);
}
