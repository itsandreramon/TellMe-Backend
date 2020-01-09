/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.services;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import com.tellme.backend.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Optional<Boolean> addToDatabase(User user) {
    return userRepository.addUserToDatabase(user);
  }

  public Optional<User> getUserByUid(String uid) {
    return userRepository.getUserByUid(uid);
  }

  public List<User> getAllUsersByQuery(String query, int limit) {
    return userRepository.getAllUsersByQuery(query, limit);
  }

  public Optional<AuthUser> getAuthUserByUid(String uid) {
    return userRepository.getAuthUserByUid(uid);
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  public Optional<Boolean> followUserByUid(String userUid, String userToFollowUid) {
    return userRepository.followUserByUid(userUid, userToFollowUid);
  }

  public Optional<Boolean> updateUser(User updatedUser) {
    return userRepository.updateUser(updatedUser);
  }

  public Optional<Boolean> deleteUserByUid(String uid) {
    return userRepository.deleteUserByUid(uid);
  }

  public List<User> getAllUsers() {
    return userRepository.getAllUsers();
  }

  public List<User> getFollowingByUserUid(String userUid) {
    return userRepository.getFollowingByUserUid(userUid);
  }

  public Optional<Boolean> followUserByUID(String uid, String uidToFollow) {
    return userRepository.followUserByUid(uid, uidToFollow);
  }
}
