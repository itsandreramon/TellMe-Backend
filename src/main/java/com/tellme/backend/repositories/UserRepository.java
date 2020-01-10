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
import com.tellme.backend.exceptions.*;
import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import com.tellme.backend.utils.Constants;
import com.tellme.backend.utils.FirebaseUtil;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
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

        throw new UserNotFoundException(username);
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

        throw new AuthUserNotFoundException(uid);
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

        throw new UserListNotFoundException();
    }

    @Override
    public Optional<Boolean> followUserByUid(String userUid, String userToFollowUid) {
        getUserByUid(userUid).orElseThrow(() -> new UserNotFoundException(userUid));
        getUserByUid(userToFollowUid).orElseThrow(() -> new UserNotFoundException(userToFollowUid));

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
                    userUpdates.put(Constants.USER_KEY_FOLLOWING, following.stream().distinct().collect(Collectors.toList()));
                    ApiFuture<WriteResult> updateFuture = userCollection.document(document.getId()).update(userUpdates);

                    updateFuture.get();
                    addUserToFollowerList(userToFollowUid, userUid);

                    return Optional.of(true);
                } else {
                    // double check since the data could have changed already
                    throw new UserNotFoundException(userUid);
                }
            } else {
                throw new UserNotFoundException(userUid);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        throw new UserNotFollowedException(userToFollowUid);
    }

    private Optional<Boolean> addUserToFollowerList(String uid, String followerUid)
            throws UserNotUpdatedException {

        // check if both uids exist
        User user = getUserByUid(uid).orElseThrow(() -> new UserNotFoundException(uid));
        User userFollower =
                getUserByUid(followerUid).orElseThrow(() -> new UserNotFoundException(followerUid));

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
        getAuthUserByUid(uid).orElseThrow(() -> new AuthUserNotFoundException(uid));

        // do not add multiple users with same uid
        try {
            getUserByUid(uid);
            throw new UserAlreadyExistsException(uid);
        } catch (UserNotFoundException e) {
            // ok
        }

        ApiFuture<DocumentReference> addFuture = userCollection.add(userToInsert);

        try {
            addFuture.get();
            return Optional.of(true);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new UserNotAddedException(uid);
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

        throw new UserNotFoundException(uid);
    }

    @Override
    public Optional<Boolean> updateUser(User updatedUser) {
        System.err.println(updatedUser);

        User userToUpdateBackUp =
                getUserByUid(updatedUser.getUid())
                        .orElseThrow(() -> new UserNotFoundException(updatedUser.getUid()));

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

        throw new UserNotUpdatedException(updatedUser.getUid());
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

        throw new AuthUserNotFoundException(uid);
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

        throw new UserNotDeletedException(uid);
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

        throw new UserListNotFoundException();
    }

    @Override
    public List<User> getFollowingByUserUid(String userUid) {
        User user = getUserByUid(userUid).orElseThrow(() -> new UserNotFoundException(userUid));

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
