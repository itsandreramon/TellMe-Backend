package com.tellme.backend.utils;

import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;

public class TransformationUtil {

    public static FeedItem transformToFeedItem(Tell tell, User user) {
        if (tell == null || user == null) return null;

        return FeedItem.builder()
                .id(tell.getId())
                .question(tell.getQuestion())
                .reply(tell.getReply())
                .replyDate(tell.getReplyDate())
                .receiverAvatar(user.getAvatar())
                .receiverUsername(user.getUsername())
                .build();
    }
}