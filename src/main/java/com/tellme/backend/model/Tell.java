/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import com.tellme.backend.validation.ValidDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;


@Document(collection = "tells")
@Data
@AllArgsConstructor
@ToString
public class Tell {

    @Id
    @NotBlank
    private final String id;

    @NotBlank
    private final String senderUid;

    @NotBlank
    private final String receiverUid;

    @NotBlank
    private final String question;

    private final String reply;

    @ValidDate
    @NotBlank
    private final String sendDate;

    @ValidDate
    private final String replyDate;
}
