/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.utils;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.auth.UserRecord;
import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseUtil {

  public static Tell mapDocumentToTell(DocumentSnapshot document) {
    String id = String.valueOf(document.get(Constants.TELL_KEY_ID));
    String question = String.valueOf(document.get(Constants.TELL_KEY_QUESTION));
    String reply = String.valueOf(document.get(Constants.TELL_KEY_REPLY));
    String authorUid = String.valueOf(document.get(Constants.TELL_KEY_SENDER_UID));
    String receiverUid = String.valueOf(document.get(Constants.TELL_KEY_RECEIVER_UID));
    String timestampSend = String.valueOf(document.get(Constants.TELL_KEY_SEND_DATE));
    String timestampReply = String.valueOf(document.get(Constants.TELL_KEY_REPLY_DATE));

    ZonedDateTime sendDate = DateUtils.convertStringToDate(timestampSend);
    ZonedDateTime replyDate = DateUtils.convertStringToDate(timestampReply);

    String sendDateString = null;
    String replyDateString = null;

    if (sendDate != null) sendDateString = sendDate.toString();
    if (replyDate != null) replyDateString = replyDate.toString();

    return Tell.builder()
        .id(id)
        .senderUid(authorUid)
        .receiverUid(receiverUid)
        .question(question)
        .reply(reply)
        .sendDate(sendDateString)
        .replyDate(replyDateString)
        .build();
  }

  public static User mapDocumentToUser(DocumentSnapshot document) {
    List<String> following = (List<String>) document.get(Constants.USER_KEY_FOLLOWING);
    List<String> followers = (List<String>) document.get(Constants.USER_KEY_FOLLOWERS);
    String about = String.valueOf(document.get(Constants.USER_KEY_ABOUT));
    String avatar = String.valueOf(document.get(Constants.USER_KEY_AVATAR));
    String name = String.valueOf(document.get(Constants.USER_KEY_NAME));
    String uid = String.valueOf(document.get(Constants.USER_KEY_UID));
    String username = String.valueOf(document.get(Constants.USER_KEY_USERNAME));
    String email = String.valueOf(document.get(Constants.USER_KEY_EMAIL));

    if (following == null) following = Collections.emptyList();
    if (followers == null) followers = Collections.emptyList();
    if (avatar.equals("null")) avatar = null;

    return User.builder()
        .following(following)
        .followers(followers)
        .about(about)
        .name(name)
        .uid(uid)
        .username(username)
        .email(email)
        .avatar(avatar)
        .build();
  }

  public static Map<String, Object> mapUserToMap(User user) {
    Map<String, Object> userMap = new HashMap<>();

    userMap.put(Constants.USER_KEY_UID, user.getUid());
    userMap.put(Constants.USER_KEY_NAME, user.getName());
    userMap.put(Constants.USER_KEY_AVATAR, user.getAvatar());
    userMap.put(Constants.USER_KEY_EMAIL, user.getEmail());
    userMap.put(Constants.USER_KEY_USERNAME, user.getUsername());
    userMap.put(Constants.USER_KEY_FOLLOWING, user.getFollowing());
    userMap.put(Constants.USER_KEY_FOLLOWERS, user.getFollowers());
    userMap.put(Constants.USER_KEY_ABOUT, user.getAbout());

    System.err.println(userMap + "map map");

    return userMap;
  }

  public static AuthUser mapRecordToAuthUser(UserRecord userRecord) {
    String email = userRecord.getEmail();
    return AuthUser.builder().email(email).build();
  }
}
