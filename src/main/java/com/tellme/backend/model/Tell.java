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
public class Tell {

    @Id private final String id;
    private final String senderUid;
    private final String receiverUid;
    private final String question;
    private final String reply;
    private final String sendDate;
    private final String replyDate;
}
