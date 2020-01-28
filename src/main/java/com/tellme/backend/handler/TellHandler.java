/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.handler;

import com.tellme.backend.model.Tell;
import com.tellme.backend.service.TellService;
import com.tellme.backend.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TellHandler {

	private final TellService tellService;

	public Mono<ServerResponse> save(ServerRequest request) {
		Mono<Tell> tellMono = request.bodyToMono(Tell.class).doOnNext(ValidationUtil::validate)
				.flatMap(tellService::save);

		return tellMono.then(ServerResponse.ok().bodyValue(true))
				.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
	}

	public Mono<ServerResponse> update(ServerRequest request) {
		Mono<Tell> tellMono = request.bodyToMono(Tell.class).doOnNext(ValidationUtil::validate)
				.flatMap(tellService::update);

		return tellMono.then(ServerResponse.ok().bodyValue(true))
				.switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
	}

	public Mono<ServerResponse> findById(ServerRequest request) {
		String id = request.pathVariable("id");
		Mono<Tell> tellMono = tellService.findById(id);

		return tellMono.flatMap(tell -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(tell))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

	public Mono<ServerResponse> findAll(ServerRequest request) {
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(tellService.findAll(), Tell.class);
	}

	public Mono<ServerResponse> findByReceiverUid(ServerRequest request) {
		String receiverUid = request.pathVariable("receiverUid");

		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(tellService.findByReceiverUid(receiverUid), Tell.class);
	}

	public Mono<ServerResponse> findBySenderUid(ServerRequest request) {
		String senderUid = request.pathVariable("senderUid");

		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(tellService.findBySenderUid(senderUid),
				Tell.class);
	}

	public Mono<ServerResponse> deleteById(ServerRequest request) {
		String id = request.pathVariable("id");

		Mono<Void> deleteMono = tellService.findById(id).flatMap(tell -> tellService.deleteById(id));

		return deleteMono.then(ServerResponse.ok().bodyValue(true))
				.switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(false));
	}
}
