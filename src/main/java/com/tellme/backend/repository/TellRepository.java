package com.tellme.backend.repository;

import com.tellme.backend.model.Tell;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TellRepository extends ReactiveCrudRepository<Tell, String> {

    Flux<Tell> findByReceiverUid(String receiverUid);

    Flux<Tell> findBySenderUid(String senderUid);
}
