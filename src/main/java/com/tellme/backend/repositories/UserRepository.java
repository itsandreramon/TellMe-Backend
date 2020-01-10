/*
 * Copyright 2020 - André Thiele
 *
 * Fachbereich Informatik und Medien
 * Technische Hochschule Brandenburg
 */

package com.tellme.backend.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.cloud.FirestoreClient;
import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import com.tellme.backend.utils.Constants;
import com.tellme.backend.utils.FirebaseUtil;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("FirestoreRepository")
public class UserRepository implements UserDao {

  private final CollectionReference userCollection;

  public UserRepository() {
    Firestore database = FirestoreClient.getFirestore();
    userCollection = database.collection("users");
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    Query query = userCollection.whereEqualTo(Constants.USER_KEY_USERNAME, username).limit(1);
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    try {

      // querySnapshot.get() blocks
      if (querySnapshot.get().getDocuments().size() > 0) {
        QueryDocumentSnapshot document = querySnapshot.get().getDocuments().get(0);

        if (document.exists()) {
          User user = FirebaseUtil.mapDocumentToUser(document);
          return Optional.of(user);
        }
      }
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }

    throw new ResourceNotFoundException(username);
  }

  @Override
  public Optional<AuthUser> getAuthUserByUid(String uid) {
    try {
      UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
      AuthUser authUser = FirebaseUtil.mapRecordToAuthUser(userRecord);
      return Optional.of(authUser);
    } catch (FirebaseAuthException e) {
      System.err.println(e.getMessage());
    }

    throw new ResourceNotFoundException(uid);
  }

  @Override
  public List<User> getAllUsersByQuery(String query, int limit) {
    Query searchQuery =
        userCollection.whereGreaterThanOrEqualTo(Constants.USER_KEY_USERNAME, query).limit(limit);
    ApiFuture<QuerySnapshot> querySnapshot = searchQuery.get();

    try {

      // querySnapshot.get() blocks
      if (querySnapshot.get().getDocuments().size() > 0) {

        return querySnapshot.get().getDocuments().stream()
            .map(FirebaseUtil::mapDocumentToUser)
            .filter(user -> user.getUsername().startsWith(query))
            .collect(Collectors.toList());

      } else {
        // no documents exist in user collection
        return new ArrayList<>();
      }
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }

    throw new ResourceNotFoundException();
  }

  @Override
  public Optional<Boolean> followUserByUid(String userUid, String userToFollowUid) {
    getUserByUid(userUid).orElseThrow(() -> new ResourceNotFoundException(userUid));
    getUserByUid(userToFollowUid).orElseThrow(() -> new ResourceNotFoundException(userToFollowUid));

    Query query = userCollection.whereEqualTo(Constants.USER_KEY_UID, userUid).limit(1);
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    try {
      if (querySnapshot.get().getDocuments().size() > 0) {

        // querySnapshot.get() blocks
        QueryDocumentSnapshot document = querySnapshot.get().getDocuments().get(0);

        if (document.exists()) {

          // Retrieve current user
          User userObject = document.toObject(User.class);
          Map<String, Object> userUpdates = new HashMap<>();

          // Add userToFollowUid to following list
          List<String> following = userObject.getFollowing();
          if (following == null) following = new ArrayList<>();
          following.add(userToFollowUid);

          // Set updated list
          userUpdates.put(
              Constants.USER_KEY_FOLLOWING,
              following.stream().distinct().collect(Collectors.toList()));
          ApiFuture<WriteResult> updateFuture =
              userCollection.document(document.getId()).update(userUpdates);

          updateFuture.get();
          addUserToFollowerList(userToFollowUid, userUid);

          return Optional.of(true);
        } else {
          // double check since the data could have changed already
          throw new ResourceNotFoundException(userUid);
        }
      } else {
        throw new ResourceNotFoundException(userUid);
      }
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }

    throw new IllegalStateException(userToFollowUid);
  }

  private Optional<Boolean> addUserToFollowerList(String uid, String followerUid)
      throws IllegalStateException {

    // check if both uids exist
    User user = getUserByUid(uid).orElseThrow(() -> new ResourceNotFoundException(uid));
    User userFollower =
        getUserByUid(followerUid).orElseThrow(() -> new ResourceNotFoundException(followerUid));

    List<String> userFollowers = user.getFollowers();

    userFollowers.add(followerUid);
    user.setFollowers(userFollowers.stream().distinct().collect(Collectors.toList()));

    updateUser(user);
    return Optional.of(true);
  }

  @Override
  public Optional<Boolean> addUserToDatabase(User userToInsert) {
    String uid = userToInsert.getUid();

    // only add users that are registered with valid uid
    getAuthUserByUid(uid).orElseThrow(() -> new ResourceNotFoundException(uid));

    // do not add multiple users with same uid
    try {
      getUserByUid(uid);
      throw new IllegalStateException(uid);
    } catch (ResourceNotFoundException e) {
      // ok
    }

    ApiFuture<DocumentReference> addFuture = userCollection.add(userToInsert);

    try {
      addFuture.get();
      return Optional.of(true);
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
      throw new IllegalStateException(uid);
    }
  }

  @Override
  public Optional<User> getUserByUid(String uid) {
    Query query = userCollection.whereEqualTo(Constants.USER_KEY_UID, uid).limit(1);
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    try {

      // querySnapshot.get() blocks
      if (querySnapshot.get().getDocuments().size() > 0) {
        QueryDocumentSnapshot document = querySnapshot.get().getDocuments().get(0);

        if (document.exists()) {
          return Optional.of(FirebaseUtil.mapDocumentToUser(document));
        }
      }
    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }

    throw new ResourceNotFoundException(uid);
  }

  @Override
  public Optional<Boolean> updateUser(User updatedUser) {
    System.err.println(updatedUser);

    User userToUpdateBackUp =
        getUserByUid(updatedUser.getUid())
            .orElseThrow(() -> new ResourceNotFoundException(updatedUser.getUid()));

    // Delete and re-add updated user.
    // maybe a bit slower but more consistent
    // and less error prone as attributes may change.
    boolean deleted = deleteUserByUid(updatedUser.getUid()).orElse(false);
    boolean added = addUserToDatabase(updatedUser).orElse(false);

    if (deleted) {
      if (added) {
        return Optional.of(true);
      } else {
        // re-add user
        addUserToDatabase(userToUpdateBackUp);
      }
    }

    throw new IllegalStateException(updatedUser.getUid());
  }

  @Override
  public Optional<Boolean> deleteAuthUserByUid(String uid) {
    if (getAuthUserByUid(uid).isPresent()) {
      try {
        FirebaseAuth.getInstance().deleteUser(uid);
        return Optional.of(true);
      } catch (FirebaseAuthException e) {
        e.printStackTrace();
      }
    }

    throw new ResourceNotFoundException(uid);
  }

  @Override
  public Optional<Boolean> deleteUserByUid(String uid) {
    Query query = userCollection.whereEqualTo(Constants.USER_KEY_UID, uid).limit(1);
    ApiFuture<QuerySnapshot> querySnapshot = query.get();

    if (getUserByUid(uid).isPresent()) {
      try {

        // querySnapshot.get() blocks
        if (querySnapshot.get().getDocuments().size() > 0) {
          String documentToDeleteId = querySnapshot.get().getDocuments().get(0).getId();
          userCollection.document(documentToDeleteId).delete();
          return Optional.of(true);
        }
      } catch (ExecutionException | InterruptedException e) {
        e.printStackTrace();
      }
    }

    throw new IllegalStateException(uid);
  }

  @Override
  public List<User> getAllUsers() {
    // asynchronously retrieve multiple documents
    ApiFuture<QuerySnapshot> future = userCollection.get();

    try {
      // future.get() blocks on response
      List<QueryDocumentSnapshot> documents = future.get().getDocuments();

      return documents.stream()
          .map(document -> document.toObject(User.class))
          .collect(Collectors.toList());

    } catch (ExecutionException | InterruptedException e) {
      e.printStackTrace();
    }

    throw new ResourceNotFoundException();
  }

  @Override
  public List<User> getFollowingByUserUid(String userUid) {
    User user = getUserByUid(userUid).orElseThrow(() -> new ResourceNotFoundException(userUid));

    return user.getFollowing().stream()
        .map(
            uid -> {
              try {
                return getUserByUid(uid);
              } catch (Exception e) {
                return null;
              }
            })
        .filter(Objects::nonNull)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());
  }
}
