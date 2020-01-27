/*
 * Copyright 2020 - André Ramon Thiele
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
    RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/users", handler::findAll)
                .GET("/users/uid/{uid}", handler::findById)
                .GET("/users/username/{username}", handler::findByUsername)
                .GET("/users/search/{query}", handler::findByUsernameLike)
                .GET("/users/{id}/feed", handler::getFeedByUserId)
                .GET("/users/{id}/inbox", handler::getInboxByUserId)
                .GET("/users/{id}/auth", handler::findAuthUserById)
                .POST("/users", handler::save)
                .build();
    }
}
