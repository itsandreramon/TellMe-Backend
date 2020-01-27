/*
 * Copyright 2020 - André Ramon Thiele
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
                .POST("/tells", handler::save)
                .GET("/tells", handler::findAll)
                .build();
    }
}
