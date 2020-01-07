package com.tellme.backend.web;

import com.tellme.backend.model.FeedItem;
import com.tellme.backend.model.Tell;
import com.tellme.backend.services.FeedService;
import com.tellme.backend.services.InboxService;
import com.tellme.backend.services.TellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("api/v1/tellme")
@RestController
public class TellController {

    private final TellService tellService;
    private final InboxService inboxService;
    private final FeedService feedService;

    public TellController(TellService tellService, FeedService feedService, InboxService inboxService) {
        this.tellService = tellService;
        this.feedService = feedService;
        this.inboxService = inboxService;
    }

    @GetMapping("/tells")
    public List<Tell> getAllTells() {
        return tellService.getAllTells();
    }

    @GetMapping("/tells/id/{id}")
    public Optional<Tell> getTellById(@PathVariable("id") String id) {
        return tellService.findTellById(id);
    }

    @GetMapping("/tells/user/{uid}/inbox")
    public List<Tell> getInboxByUser(@PathVariable("uid") String uid) {
        return inboxService.getInboxByUser(uid);
    }

    @GetMapping("/tells/user/{uid}/feed")
    public List<FeedItem> getFeedByUserUid(@PathVariable("uid") String uid) {
        return feedService.getFeedByUserUid(uid);
    }

    @GetMapping("/tells/sender/{uid}")
    public List<Tell> getAllTellsBySender(@PathVariable("uid") String uid) {
        return tellService.getAllTellsBySender(uid);
    }

    @PostMapping("/tells")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Boolean> insertTell(@Valid @RequestBody Tell tell) {
        return tellService.insertTell(tell)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/tells")
    public Boolean updateTell(@RequestBody Tell tell) {
        return tellService.updateTell(tell);
    }

    @DeleteMapping("/tells/id/{id}")
    public Boolean deleteTellById(@PathVariable("id") String id) {
        return tellService.deleteTellById(id);
    }
}
