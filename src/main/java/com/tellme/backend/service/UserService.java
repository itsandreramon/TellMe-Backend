package com.tellme.backend.service;

import com.tellme.backend.model.User;
import com.tellme.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }
}
