package com.tellme.backend.router;

import com.tellme.backend.handler.HelloHandler;
import com.tellme.backend.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class HelloRouter {

    @Bean
    RouterFunction<ServerResponse> helloRoutes(HelloHandler handler) {
        return RouterFunctions.route()
                .GET("/", handler::hello)
                .build();
    }
}
