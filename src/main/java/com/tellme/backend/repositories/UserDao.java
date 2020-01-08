/*
 * Copyright 2020 - André Thiele, Benjamin Will
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.repositories;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<Boolean> addUserToDatabase(User user);

  Optional<User> getUserByUid(String uid);

  List<User> getAllUsersByQuery(String query, int limit);

  List<User> getAllUsers();

  Optional<AuthUser> getAuthUserByUid(String uid);

  Optional<User> getUserByUsername(String username);

  Optional<Boolean> followUserByUid(String userUid, String userToFollowUid);

  Optional<Boolean> updateUser(User user);

  Optional<Boolean> deleteUserByUid(String uid);

  Optional<Boolean> deleteAuthUserByUid(String uid);

  List<User> getFollowingByUserUid(String userUid);
}
