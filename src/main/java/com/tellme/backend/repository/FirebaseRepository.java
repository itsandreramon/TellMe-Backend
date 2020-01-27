/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.tellme.backend.model.AuthUser;
import com.tellme.backend.util.FirebaseUtil;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class FirebaseRepository {

    public Mono<AuthUser> findAuthUserById(String id) {
        try {

            // block?
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(id);

            return Mono.just(userRecord)
                    .map(FirebaseUtil::mapRecordToAuthUser);

        } catch (FirebaseAuthException e) {
            return Mono.empty();
        }
    }
}
