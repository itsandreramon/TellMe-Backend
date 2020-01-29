/*
 * Copyright 2020 - André Thiele, Benjamin Will
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserService userService;
    private final FeedService feedService;
    private final InboxService inboxService;

    /**
     * Saves a given {@link User} to the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class)
                .doOnNext(ValidationUtil::validate)
                .flatMap(userService::save);

        return userMono
                .flatMap(user -> ServerResponse.status(HttpStatus.CREATED).bodyValue(true))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
    }

    /**
     * Updates a given {@link User} in the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> updateUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class)
                .doOnNext(ValidationUtil::validate)
                .flatMap(userService::save);

        return userMono
                .flatMap(user -> ServerResponse.status(HttpStatus.OK).bodyValue(true))
                .onErrorResume(e -> ServerResponse.status(HttpStatus.BAD_REQUEST).bodyValue(false));
    }

    /**
     * Returns a {@link User} that matches a given uid.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        return userMono
                .then(ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userMono, User.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Returns all {@link User}s from the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        Flux<User> userFlux = userService.findAll();

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }

    /**
     * Returns a {@link User} that matches a given username.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getUserByUsername(ServerRequest request) {
        String username = request.pathVariable("username");
        Mono<User> userMono = userService.findByUsername(username);

        return userMono
                .then(ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(userMono, User.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Returns all {@link User}s that match a given username query.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUsersByQuery(ServerRequest request) {
        String query = request.pathVariable("query");
        Integer limit = Integer.valueOf(request.pathVariable("limit"));

        Flux<User> userFlux = userService.findByUsernameLike(query, limit);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userFlux, User.class);
    }

    /**
     * Returns an {@link AuthUser} that matches a given uid.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAuthUserByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<AuthUser> authUserMono = userService.findAuthUserById(uid);

        return authUserMono
                .then(ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(authUserMono, AuthUser.class))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    /**
     * Deletes a {@link User} that matches a given uid from the database.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUserByUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Mono<User> userMono = userService.findById(uid);

        Mono<Void> deleteMono = userMono
                .flatMap(user -> userService.deleteById(uid));

        return deleteMono
                .then(ServerResponse.status(HttpStatus.OK).bodyValue(true))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(false));
    }

    /**
     * Returns the feed for a {@link User}.
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getFeedByUserUid(ServerRequest request) {
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
    public Mono<ServerResponse> getInboxByUserUid(ServerRequest request) {
        String uid = request.pathVariable("uid");
        Flux<Tell> inboxItemFlux = inboxService.getInboxByUid(uid);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(inboxItemFlux, Tell.class);
    }
}
