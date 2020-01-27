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


@Document(collection = "tells")
@Data
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Tell {

    @Id
    private String id;

    @NotBlank
    private String senderUid;

    @NotBlank
    private String receiverUid;

    @NotBlank
    private String question;

    private String reply;

    @ValidDate
    @NotBlank
    private String sendDate;

    @ValidDate
    private String replyDate;
}
