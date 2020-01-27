/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import com.tellme.backend.validation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@ToString
public class FeedItem {

    @Id
    @NotBlank
    private final String id;

    private final String receiverAvatar;

    @NotBlank
    private final String receiverUsername;

    @NotBlank
    private final String question;

    @NotBlank
    private final String reply;

    @ValidDate
    @NotBlank
    private final String replyDate;
}
