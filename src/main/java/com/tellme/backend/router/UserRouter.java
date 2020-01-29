/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.router;

import com.tellme.backend.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> routes(UserHandler handler) {
        return RouterFunctions.route()
                .path("/api/v2/", b1 -> b1
                        .path("/users", b2 -> b2
                                .POST("/", handler::saveUser)
                                .PUT("/", handler::updateUser)
                                .GET("/", handler::getAllUsers)
                                .GET("/username/{username}", handler::getUserByUsername)
                                .GET("/query/{query}/{limit}", handler::getAllUsersByQuery)
                                .GET("/uid/{uid}", handler::getUserByUid)
                                .GET("/uid/{uid}/feed", handler::getFeedByUserUid)
                                .GET("/uid/{uid}/inbox", handler::getInboxByUserUid)
                                .GET("/uid/{uid}/auth", handler::getAuthUserByUid)
                                .DELETE("/uid/{uid}", handler::deleteUserByUid)
                        )
                )
                .build();
    }
}