package com.tellme.backend.service;

import com.tellme.backend.model.Tell;
import com.tellme.backend.repository.TellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class InboxService {

    private final TellRepository tellRepository;

    public Flux<Tell> getInboxByUser(String id) {
        return tellRepository.findByReceiverUid(id)
                .filter(tell -> tell.getReply().isEmpty());
    }
}
