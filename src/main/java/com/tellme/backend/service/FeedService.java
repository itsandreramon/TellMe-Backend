/*
 * Copyright 2020 - André Thiele, Benjamin Will
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
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ParallelFlux;

@Service
@RequiredArgsConstructor
public class FeedService {

	private final TellRepository tellRepository;
	private final UserRepository userRepository;

	public Flux<FeedItem> getFeedByUserId(String id) {

		Flux<String> followingFlux = userRepository.findById(id)
				.map(User::getFollowing)
				.flatMapMany(Flux::fromIterable)
				.mergeWith(Flux.just(id));

		ParallelFlux<Tell> tellFlux = followingFlux
				.parallel(4)
				.flatMap(tellRepository::findByReceiverUid)
				.filter(tell -> !tell.getReply().isEmpty());

		ParallelFlux<FeedItem> feedItemFlux = tellFlux
				.flatMap(tell -> userRepository.findById(tell.getReceiverUid())
				.map(user -> TransformationUtil.feedItemFrom(user, tell)));

		return feedItemFlux.sequential();
	}
}
