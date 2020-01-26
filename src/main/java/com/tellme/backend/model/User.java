/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@ToString
public class User {

    @Id private String uid;
    private final String name;
    private final String username;
    private final String email;
    private final String avatar;
    private final String about;
    private final List<String> following;
    private final List<String> followers;
}
