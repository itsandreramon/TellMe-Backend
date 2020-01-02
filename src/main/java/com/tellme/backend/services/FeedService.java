package com.tellme.backend.services;

import com.tellme.backend.exceptions.UserNotFoundException;
import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.User;
import com.tellme.backend.repositories.TellRepository;
import com.tellme.backend.repositories.UserRepository;
import com.tellme.backend.utils.TransformationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FeedService {

    private final TellRepository tellRepository;
    private final UserRepository userRepository;

    public FeedService(TellRepository tellRepository, UserRepository userRepository) {
        this.tellRepository = tellRepository;
        this.userRepository = userRepository;
    }

    public List<FeedItem> getFeedByUserUid(String uid) {
        var follows = userRepository.getFollowsByUserUid(uid);
        var tellFeed = tellRepository.getTellFeedByUserUid(
                uid,
                follows.stream().map(User::getUid).collect(Collectors.toList())
        );
        System.out.println(tellFeed);

        var feed = tellFeed.stream()
                .map(tell -> {
                    var user = userRepository.getUserByUid(tell.getReceiverUid());
                    if (user.isEmpty()) throw new UserNotFoundException(tell.getReceiverUid());

                    var authUser = userRepository.getAuthUserByUid(user.get().getUid());
                    if (authUser.isEmpty()) throw new UserNotFoundException(tell.getReceiverUid());

                    var feedItem = TransformationUtil.transformToFeedItem(tell, user.get());

                    return feedItem;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return feed;
    }
}
