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
        return tellRepository.save(tell);
    }

    public Mono<Void> deleteAll() {
        return tellRepository.deleteAll();
    }
}
