/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.services;

import com.tellme.backend.model.FeedItem;
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
    var follows =
        userRepository.getFollowingByUserUid(uid).stream()
            .map(User::getUid)
            .collect(Collectors.toList());

    var tellFeed = tellRepository.getTellFeedByUserUid(uid, follows);

    System.out.println(tellFeed);

    var feed =
        tellFeed.stream()
            .map(
                tell -> {
                  try {
                    System.out.println("trying...");
                    var user = userRepository.getUserByUid(tell.getReceiverUid());
                    var authUser = userRepository.getAuthUserByUid(user.get().getUid());
                    var feedItem = TransformationUtil.transformToFeedItem(tell, user.get());

                    return feedItem;
                  } catch (Exception e) {
                    System.out.println("exception...");
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

    return feed;
  }
}
