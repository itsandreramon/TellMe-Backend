/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tellme.backend.utils.Constants;
import com.tellme.backend.utils.ValidDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedItem {

  @NotBlank
  @JsonProperty(Constants.FEED_ITEM_KEY_ID)
  private String id;

  @NotBlank
  @JsonProperty(Constants.FEED_ITEM_KEY_RECEIVER_AVATAR)
  private String receiverAvatar;

  @NotBlank
  @JsonProperty(Constants.FEED_ITEM_KEY_RECEIVER_USERNAME)
  private String receiverUsername;

  @NotBlank
  @JsonProperty(Constants.FEED_ITEM_KEY_QUESTION)
  private String question;

  @NotNull
  @JsonProperty(Constants.FEED_ITEM_KEY_REPLY)
  private String reply;

  @NotNull
  @ValidDate
  @JsonProperty(Constants.FEED_ITEM_KEY_REPLY_DATE)
  private String replyDate;
}
