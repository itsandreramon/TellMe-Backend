/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.handler;

import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import com.tellme.backend.service.TellService;
import com.tellme.backend.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TellHandler {

	private final TellService tellService;

	/**
	 * Saves a given {@link Tell} to the database.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> saveTell(ServerRequest request) {
		Mono<Tell> tellMono = request.bodyToMono(Tell.class)
				.doOnNext(ValidationUtil::validate)
				.flatMap(tellService::save);

		return tellMono
				.then(ServerResponse.status(HttpStatus.CREATED).bodyValue(true))
				.onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
	}

	/**
	 * Updates a given {@link User} in the database.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> updateTell(ServerRequest request) {
		Mono<Tell> tellMono = request.bodyToMono(Tell.class)
				.doOnNext(ValidationUtil::validate)
				.flatMap(tellService::update);

		return tellMono
				.then(ServerResponse.status(HttpStatus.OK).bodyValue(true))
				.onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
	}

	/**
	 * Returns a {@link Tell} that matches a given id.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getTellById(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Tell> tellMono = tellService.findById(id);

		return tellMono
				.flatMap(tell -> ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tellMono, Tell.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	/**
	 * Returns all {@link Tell}s from the database.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getAllTells(ServerRequest request) {
		Flux<Tell> tellFlux = tellService.findAll();

		return ServerResponse
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(tellFlux, Tell.class);
	}

	/**
	 * Returns all {@link Tell}s that correspond to a given receiver uid.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getAllTellsByReceiverUid(ServerRequest request) {
		String receiverUid = request.pathVariable("receiverUid");
		Flux<Tell> tellFlux = tellService.findByReceiverUid(receiverUid);

		return ServerResponse
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(tellFlux, Tell.class);
	}

	/**
	 * Returns all {@link Tell}s that correspond to a given sender uid.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getAllTellsBySenderUid(ServerRequest request) {
		String senderUid = request.pathVariable("senderUid");
		Flux<Tell> tellFlux = tellService.findBySenderUid(senderUid);

		return ServerResponse
				.status(HttpStatus.OK)
				.contentType(MediaType.APPLICATION_JSON)
				.body(tellFlux, Tell.class);
	}

	/**
	 * Deletes a {@link Tell} that matches a given id from the database.
	 *
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> deleteTellById(ServerRequest request) {
		String id = request.pathVariable("id");

		Mono<Void> deleteMono = tellService.findById(id)
				.flatMap(tell -> tellService.deleteById(id));

		return deleteMono
				.then(ServerResponse.status(HttpStatus.OK).bodyValue(true))
				.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(false));
	}
}
