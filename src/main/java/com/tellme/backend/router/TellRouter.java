/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.router;

import com.tellme.backend.handler.TellHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TellRouter {

    @Bean
    RouterFunction<ServerResponse> tellRoutes(TellHandler handler) {
        return RouterFunctions.route()
                .path("/api/v2/", b1 -> b1
                        .path("/tells", b2 -> b2
                                .POST("/", handler::saveTell)
                                .PUT("/", handler::updateTell)
                                .GET("/", handler::getAllTells)
                                .GET("/id/{id}", handler::getTellById)
                                .DELETE("/id/{id}", handler::deleteTellById)
                                .GET("/sender/{senderUid}", handler::getAllTellsBySenderUid)
                                .GET("/receiver/{receiverUid}", handler::getAllTellsByReceiverUid)
                        )
                )
                .build();
    }
}