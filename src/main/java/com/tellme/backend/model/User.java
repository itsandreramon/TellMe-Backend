/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@ToString
public class User {

    @Id
    @NotBlank
    private final String uid;

    @NotBlank
    private final String name;

    @NotBlank
    private final String username;

    @NotBlank
    private final String email;

    private final String avatar;

    private final String about;

    @NotNull
    private final List<String> following;

    @NotNull
    private final List<String> followers;
}
