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
    private String uid;

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    private String avatar;

    @NotNull
    private String about;

    @NotNull
    private List<String> following;

    @NotNull
    private List<String> followers;
}
