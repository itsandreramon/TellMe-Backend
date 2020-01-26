package com.tellme.backend.repository;

import com.tellme.backend.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, String> {

}
