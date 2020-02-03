/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.service;

import com.tellme.backend.model.ReplyItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.repository.TellRepository;
import com.tellme.backend.repository.UserRepository;
import com.tellme.backend.util.TransformationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class RepliesService {

    private final TellRepository tellRepository;
    private final UserRepository userRepository;

    public Flux<ReplyItem> getRepliesByUid(String id) {
        Flux<Tell> tellFlux = tellRepository.findBySenderUid(id)
                .filter(tell -> !tell.getReply().isEmpty());

        Flux<ReplyItem> replyItemFlux = tellFlux
                .flatMap(tell -> userRepository.findById(tell.getReceiverUid())
						.map(user -> TransformationUtil.replyItemFrom(user, tell)));

        return replyItemFlux;
    }
}
