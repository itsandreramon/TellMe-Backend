package com.tellme.backend.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tellme.backend.model.Tell;
import com.tellme.backend.utils.Constants;
import com.tellme.backend.utils.DateUtils;
import com.tellme.backend.utils.FirebaseUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class TellRepository implements TellDao {

    private final CollectionReference tellCollection;

    public TellRepository() {
        Firestore database = FirestoreClient.getFirestore();
        tellCollection = database.collection("tells");
    }

    @Override
    public Optional<Tell> findTellById(String id) {
        Optional<Tell> tellOptional = Optional.empty();

        ApiFuture<DocumentSnapshot> future = tellCollection.document(id).get();

        try {
            // future.get() blocks
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                tellOptional = Optional.of(FirebaseUtil.mapDocumentToTell(document));
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return tellOptional;
    }

    @Override
    public Boolean deleteTellById(String id) {
        boolean deleted = false;

        if (findTellById(id).isPresent()) {
            ApiFuture<WriteResult> writeResult = tellCollection.document(id).delete();

            try {
                // writeResult.get() blocks
                writeResult.get();

                deleted = true;
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        return deleted;
    }

    @Override
    public Boolean updateTell(Tell tell) {
        boolean updated = false;

        if (findTellById(tell.getId()).isPresent()) {
            tellCollection.document(tell.getId()).set(tell);
            updated = true;
        }

        return updated;
    }

    @Override
    public Boolean removeReply(String id) {
        boolean removed = false;

        if (findTellById(id).isPresent()) {
            DocumentReference docRef = tellCollection.document(id);

            Map<String, Object> updates = new HashMap<>();

            updates.put(Constants.TELL_KEY_REPLY, FieldValue.delete());
            ApiFuture<WriteResult> writeResult = docRef.update(updates);

            try {
                // writeResult.get() blocks
                writeResult.get();
                removed = true;
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                removed = false;
            }
        }

        return removed;
    }

    @Override
    public Optional<Boolean> insertTell(Tell tellToInsert) {
        DocumentReference reference = tellCollection.document();
        Map<String, Object> tell = new HashMap<>();

        tell.put(Constants.TELL_KEY_ID, reference.getId());
        tell.put(Constants.TELL_KEY_SENDER_UID, tellToInsert.getSenderUid());
        tell.put(Constants.TELL_KEY_RECEIVER_UID, tellToInsert.getReceiverUid());
        tell.put(Constants.TELL_KEY_QUESTION, tellToInsert.getQuestion());
        tell.put(Constants.TELL_KEY_REPLY, tellToInsert.getReply());

        var sendDate = DateUtils.convertStringToDate(tellToInsert.getSendDate()).toString();
        tell.put(Constants.TELL_KEY_SEND_DATE, sendDate);

        tell.put(Constants.TELL_KEY_REPLY_DATE, null);

        reference.set(tell);
        return Optional.of(true);
    }

    @Override
    public List<Tell> getAllTells() {
        ApiFuture<QuerySnapshot> query = tellCollection.get();

        try {
            // query.get() blocks
            QuerySnapshot querySnapshot = query.get();

            return querySnapshot.getDocuments().stream()
                    .map(FirebaseUtil::mapDocumentToTell)
                    .collect(Collectors.toList());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tell> getInboxByUser(String uid) {
        ApiFuture<QuerySnapshot> query = tellCollection
                .whereEqualTo(Constants.TELL_KEY_RECEIVER_UID, uid)
                .get();

        try {
            // query.get() blocks
            QuerySnapshot querySnapshot = query.get();

            return querySnapshot.getDocuments().stream()
                    .map(FirebaseUtil::mapDocumentToTell)
                    .filter(tell -> tell.getReply().isEmpty())
                    .collect(Collectors.toList());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tell> getAllTellsBySender(String uid) {
        ApiFuture<QuerySnapshot> query = tellCollection
                .whereEqualTo(Constants.TELL_KEY_SENDER_UID, uid)
                .get();

        try {
            // query.get() blocks
            QuerySnapshot querySnapshot = query.get();

            return querySnapshot.getDocuments().stream()
                    .map(FirebaseUtil::mapDocumentToTell)
                    .collect(Collectors.toList());

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Tell> getTellFeedByUserUid(String uid, List<String> follows) {

        // include yourself in follows list to appear also in feed
        follows.add(uid);

        return follows.stream()
                .map(this::getInboxByUser)
                .reduce(Collections.emptyList(), (a, b) -> Stream.concat(a.stream(), b.stream()).collect(Collectors.toList()))
                .stream().filter(tell -> !tell.getReply().isEmpty())
                .collect(Collectors.toList());
    }
}
