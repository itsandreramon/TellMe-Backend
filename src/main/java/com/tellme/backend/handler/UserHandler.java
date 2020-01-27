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

    /**
     * Returns all {@link User}s from the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<User> userFlux = userService.findAll();

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }

    /**
     * Returns a {@link User} that matches a given uid.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        return userMono
                .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Returns a {@link User} that matches a given username.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findByUsername(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<User> userMono = userService.findByUsername(username);

        return userMono
                .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userMono, User.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Returns all {@link User}s that match a given username query.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findByUsernameLike(ServerRequest request) {
        String query = request.pathVariable("query");
        Integer limit = Integer.valueOf(request.pathVariable("limit"));

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findByUsernameLike(query, limit), User.class);
    }

    /**
     * Returns an {@link AuthUser} that matches a given uid.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> findAuthUserByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<AuthUser> authUserMono = userService.findAuthUserById(uid);

        return authUserMono
                .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(authUserMono, AuthUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Saves a given {@link User} to the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class).doOnNext(ValidationUtil::validate)
                .flatMap(userService::save);

        return userMono
                .then(ServerResponse.status(HttpStatus.CREATED).bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
    }

    /**
     * Updates a given {@link User} in the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> update(ServerRequest request) {
        return save(request);
    }

    /**
     * Deletes a {@link User} that matches a given uid from the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        Mono<Void> deleteMono = userMono
                .flatMap(user -> userService.deleteById(uid));

        return deleteMono
                .then(ServerResponse.ok().bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(false));
    }

    /**
     * Deletes all {@link User}s from the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteAll(ServerRequest request) {
        Mono<Void> deleteMono = userService.deleteAll();

        return deleteMono
                .then(ServerResponse.ok().bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.CONFLICT).bodyValue(false));
    }

    /**
     * Returns the feed for a {@link User}.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getFeedByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Flux<FeedItem> feedItemFlux = feedService.getFeedByUserId(uid);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(feedItemFlux, FeedItem.class);
    }

    /**
     * Returns the inbox for a {@link User}.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getInboxByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Flux<Tell> inboxItemFlux = inboxService.getInboxByUid(uid);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inboxItemFlux, Tell.class);
    }
}
