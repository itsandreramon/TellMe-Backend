/*
 * Copyright 2020 - André Thiele
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
public class Tell {

  private String id;

  @NotBlank
  @JsonProperty(Constants.TELL_KEY_SENDER_UID)
  private String senderUid;

  @NotBlank
  @JsonProperty(Constants.TELL_KEY_RECEIVER_UID)
  private String receiverUid;

  @NotBlank
  @JsonProperty(Constants.TELL_KEY_QUESTION)
  private String question;

  @JsonProperty(Constants.TELL_KEY_REPLY)
  private String reply;

  @NotNull
  @ValidDate
  @JsonProperty(Constants.TELL_KEY_SEND_DATE)
  private String sendDate;

  @JsonProperty(Constants.TELL_KEY_REPLY_DATE)
  @ValidDate
  private String replyDate;

  @Override
  public String toString() {
    return "Tell{"
        + "id='"
        + id
        + '\''
        + ", senderUid='"
        + senderUid
        + '\''
        + ", receiverUid='"
        + receiverUid
        + '\''
        + ", question='"
        + question
        + '\''
        + ", reply='"
        + reply
        + '\''
        + ", sendDate='"
        + sendDate
        + '\''
        + ", replyDate='"
        + replyDate
        + '\''
        + '}';
  }
}
