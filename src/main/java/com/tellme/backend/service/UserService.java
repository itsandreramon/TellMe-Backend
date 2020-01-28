/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.service;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import com.tellme.backend.repository.FirebaseRepository;
import com.tellme.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final FirebaseRepository firebaseRepository;

	public Mono<User> findById(String id) {
		return userRepository.findById(id);
	}

	public Mono<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Flux<User> findByUsernameLike(String query, Integer limit) {
		return userRepository.findByUsernameLike(query).take(limit);
	}

	public Flux<User> findAll() {
		return userRepository.findAll();
	}

	public Mono<AuthUser> findAuthUserById(String id) {
		return firebaseRepository.findAuthUserById(id);
	}

	public Mono<User> save(User user) {
		return firebaseRepository.findAuthUserById(user.getUid()).flatMap(authUser -> userRepository.save(user));
	}

	public Mono<Void> deleteById(String id) {
		return userRepository.deleteById(id);
	}

	public Mono<Void> deleteAll() {
		return userRepository.deleteAll();
	}
}
