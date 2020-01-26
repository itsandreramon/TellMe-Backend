package com.tellme.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@AllArgsConstructor
@ToString
public class FeedItem {

    @Id private final String id;
    private final String receiverAvatar;
    private final String receiverUsername;
    private final String question;
    private final String reply;
    private final String replyDate;
}
