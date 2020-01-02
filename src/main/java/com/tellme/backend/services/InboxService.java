package com.tellme.backend.services;

import com.tellme.backend.model.Tell;
import com.tellme.backend.repositories.TellRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InboxService {

    private final TellRepository tellRepository;

    public InboxService(TellRepository tellRepository) {
        this.tellRepository = tellRepository;
    }

    public List<Tell> getInboxByUser(String uid) {
        return tellRepository.getInboxByUser(uid);
    }
}
