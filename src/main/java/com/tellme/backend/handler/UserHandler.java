/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.handler;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import com.tellme.backend.service.FeedService;
import com.tellme.backend.service.InboxService;
import com.tellme.backend.service.UserService;
import com.tellme.backend.validation.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final FeedService feedService;
    private final InboxService inboxService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<User> userFlux = userService.findAll();

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }

    public Mono<ServerResponse> findByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        return userMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByUsername(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<User> userMono = userService.findByUsername(username);

        return userMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findByUsernameLike(ServerRequest request) {
        String query = request.pathVariable("query");
        Integer limit = Integer.valueOf(request.pathVariable("limit"));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findByUsernameLike(query, limit), User.class);
    }

    public Mono<ServerResponse> findAuthUserByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<AuthUser> authUserMono = userService.findAuthUserById(uid);

        return authUserMono
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class)
                .doOnNext(ValidationUtil::validate)
                .flatMap(userService::save);

        return userMono
                .then(ServerResponse.status(HttpStatus.CREATED).bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return save(request);
    }

    public Mono<ServerResponse> deleteByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        Mono<Void> deleteMono = userMono
                .flatMap(user -> userService.deleteById(uid));

        return deleteMono
                .then(ServerResponse.ok().bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(false));
    }

    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        Mono<Void> deleteMono = userService.deleteAll();

        return deleteMono
                .flatMap(v -> ServerResponse.ok().bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.CONFLICT).bodyValue(false));
    }

    public Mono<ServerResponse> getFeedByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Flux<FeedItem> feedItemFlux = feedService.getFeedByUserId(uid);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(feedItemFlux, FeedItem.class);
    }

    public Mono<ServerResponse> getInboxByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Flux<Tell> inboxItemFlux = inboxService.getInboxByUid(uid);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inboxItemFlux, Tell.class);
    }
}
