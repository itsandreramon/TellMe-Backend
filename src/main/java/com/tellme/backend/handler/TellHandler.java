/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.handler;

import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import com.tellme.backend.service.TellService;
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

    public Mono<ServerResponse> insert(ServerRequest request) {
        Mono<Tell> tellMono = request.bodyToMono(Tell.class)
                .flatMap(tellService::save);

        return ServerResponse.status(HttpStatus.CREATED).body(tellMono, User.class);
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tellService.findAll(), Tell.class);
    }
}
