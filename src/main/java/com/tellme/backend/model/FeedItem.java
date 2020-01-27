/*
 * Copyright 2020 - André Ramon Thiele
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.model;

import com.tellme.backend.validation.ValidDate;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@ToString
public class FeedItem {

	@Id
	@NotBlank
	private String id;

	private String receiverAvatar;

	@NotBlank
	private String receiverUsername;

	@NotBlank
	private String question;

	@NotBlank
	private String reply;

	@ValidDate
	@NotBlank
	private String replyDate;
}
