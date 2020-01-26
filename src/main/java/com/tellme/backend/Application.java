package com.tellme.backend;

import com.tellme.backend.handler.UserHandler;
import com.tellme.backend.model.User;
import com.tellme.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes(UserHandler userHandler) {
        return RouterFunctions.route()
                .GET("/users", userHandler::getAll)
                .GET("/users/{id}", userHandler::getById)
                .build();
    }
}