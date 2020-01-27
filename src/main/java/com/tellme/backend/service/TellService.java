/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.service;

import com.tellme.backend.model.Tell;
import com.tellme.backend.repository.TellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TellService {

    private final TellRepository tellRepository;

    public Mono<Tell> findById(String id) {
        return tellRepository.findById(id);
    }

    public Flux<Tell> findAll() {
        return tellRepository.findAll();
    }

    public Mono<Tell> save(Tell tell) {
        // set the id to null and let the db generate one
        Tell updatedTell = tell.toBuilder().id(null).build();
        return tellRepository.save(updatedTell);
    }

    public Mono<Tell> update(Tell tell) {
        return tellRepository.save(tell);
    }

    public Mono<Void> deleteAll() {
        return tellRepository.deleteAll();
    }

    public Mono<Void> deleteById(String id) {
        return tellRepository.deleteById(id);
    }

    public Flux<Tell> findByReceiverUid(String receiverUid) {
        return tellRepository.findByReceiverUid(receiverUid);
    }

    public Flux<Tell> findBySenderUid(String senderUid) {
        return tellRepository.findByReceiverUid(senderUid);
    }
}
