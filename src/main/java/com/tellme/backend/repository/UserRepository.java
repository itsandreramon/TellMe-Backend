/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.repository;

import com.tellme.backend.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

	/**
	 * Returns a {@link User} that matches a given username.
	 *
	 * @param username
	 * @return
	 */
	Mono<User> findByUsername(String username);

	/**
	 * Returns all {@link User}s that starts with a given username.
	 *
	 * @param username
	 * @return
	 */
	Flux<User> findByUsernameLike(String username);
}
