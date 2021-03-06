/*
 * Copyright 2020 - André Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import com.tellme.backend.validation.ValidDate;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tells")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
