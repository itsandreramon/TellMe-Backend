/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.service;

import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import com.tellme.backend.repository.TellRepository;
import com.tellme.backend.repository.UserRepository;
import com.tellme.backend.util.TransformationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Log4j2
@Service
@RequiredArgsConstructor
public class FeedService {

    private final TellRepository tellRepository;
    private final UserRepository userRepository;

    public Flux<FeedItem> getFeedByUser(String id) {

        Flux<String> followingFlux = userRepository.findById(id)
                .doOnEach(log::info)
                .map(User::getFollowing)
                .flatMapMany(Flux::fromIterable);

        Flux<Tell> tellFlux = followingFlux
                .doOnEach(log::info)
                .flatMap(tellRepository::findByReceiverUid)
                .filter(tell -> !tell.getReply().isEmpty());

        Flux<FeedItem> feedItemFlux = tellFlux
                .doOnEach(log::info)
                .flatMap(tell -> userRepository
                        .findById(tell.getReceiverUid())
                        .map(user -> TransformationUtil.feedItemFrom(user, tell)));

        return feedItemFlux;
    }
}
