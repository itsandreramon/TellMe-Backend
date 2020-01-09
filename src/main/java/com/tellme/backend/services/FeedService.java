/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.services;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.model.User;
import com.tellme.backend.repositories.TellRepository;
import com.tellme.backend.repositories.UserRepository;
import com.tellme.backend.utils.TransformationUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

  private final TellRepository tellRepository;
  private final UserRepository userRepository;

  public FeedService(TellRepository tellRepository, UserRepository userRepository) {
    this.tellRepository = tellRepository;
    this.userRepository = userRepository;
  }

  public List<FeedItem> getFeedByUserUid(String uid) {

    // we only need the uids
    List<String> follows =
        userRepository.getFollowingByUserUid(uid).stream()
            .map(User::getUid)
            .collect(Collectors.toList());

    List<Tell> tellFeed = tellRepository.getTellFeedByUserUid(uid, follows);

    System.out.println(tellFeed);

    List<FeedItem> feed =
        tellFeed.stream()
            .map(
                tell -> {
                  try {
                    User user = userRepository.getUserByUid(tell.getReceiverUid()).get();
                    AuthUser authUser = userRepository.getAuthUserByUid(user.getUid()).get();
                    FeedItem feedItem = TransformationUtil.transformToFeedItem(tell, user);
                    return feedItem;
                  } catch (Exception e) {
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    return feed;
  }
}
