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
	RouterFunction<ServerResponse> routes(UserHandler handler) {
		return RouterFunctions.route()
				.path("/api/v2/", b1 -> b1.path("/users", b2 -> b2.GET("/", handler::findAll)
						.GET("/uid/{uid}", handler::findByUid).GET("/username/{username}", handler::findByUsername)
						.GET("/search/{query}/{limit}", handler::findByUsernameLike)
						.GET("/uid/{uid}/feed", handler::getFeedByUid).GET("/uid/{uid}/inbox", handler::getInboxByUid)
						.GET("/uid/{uid}/auth", handler::findAuthUserByUid).POST("/", handler::save)
						.PUT("/", handler::update).DELETE("/uid/{uid}", handler::deleteByUid)))
				.build();
	}
}
