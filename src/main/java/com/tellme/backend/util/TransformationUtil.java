/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Department of Computer Science and Media
 * University of Applied Sciences Brandenburg
 */

package com.tellme.backend.util;

import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;

public class TransformationUtil {

	public static FeedItem feedItemFrom(User user, Tell tell) {
		if (user == null || tell == null)
			return null;

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
