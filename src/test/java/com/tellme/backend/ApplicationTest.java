/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend;

import com.tellme.backend.model.Tell;
import com.tellme.backend.service.TellService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;


import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    private WebTestClient webTestClient;

    @MockBean
    private TellService tellService;

    @BeforeEach
    private void setup(ApplicationContext context) {
        webTestClient = WebTestClient
                .bindToApplicationContext(context)
                .configureClient()
                .baseUrl("/api/v2")
                .build();
    }

    @Test
    public void testSaveTell() {
        Tell tellToBeSaved = Tell.builder()
                .id("-1")
                .question("test")
                .reply("")
                .replyDate("")
                .sendDate("2020-02-01T09:12:22Z")
                .senderUid("-1")
                .receiverUid("-1")
                .build();

        when(tellService.save(tellToBeSaved)).thenReturn(Mono.just(tellToBeSaved));

        webTestClient
                .post()
                .uri("/tells")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(tellToBeSaved))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    public void testUpdateTell() {
        Tell tellToBeUpdated = Tell.builder()
                .id("-1")
                .question("test")
                .reply("")
                .replyDate("")
                .sendDate("2020-02-01T09:12:22Z")
                .senderUid("-1")
                .receiverUid("-1")
                .build();

        when(tellService.update(tellToBeUpdated)).thenReturn(Mono.just(tellToBeUpdated));

        webTestClient
                .put()
                .uri("/tells")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(tellToBeUpdated))
                .exchange()
                .expectStatus().isOk();
    }
}
