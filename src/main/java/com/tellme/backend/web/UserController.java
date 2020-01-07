package com.tellme.backend.web;

import com.tellme.backend.model.AuthUser;
import com.tellme.backend.model.User;
import com.tellme.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/tellme")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users")
    public ResponseEntity<Boolean> updateUser(@Valid @RequestBody User updatedUser) {
        return userService.updateUser(updatedUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/uid/{uid}")
    public ResponseEntity<Boolean> deleteUserByUid(@PathVariable("uid") String uid) {
        return userService.deleteUserByUid(uid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<Boolean> addToDatabase(@Valid @RequestBody User user) {
        return userService.addToDatabase(user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @CrossOrigin("localhost:8080")
    @GetMapping("/users/uid/{uid}")
    public ResponseEntity<User> getUserByUid(@PathVariable("uid") String uid) {
        return userService.getUserByUid(uid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


    @GetMapping("/users/username/{query}/{limit}")
    public List<User> getAllUsersByQuery(@PathVariable("query") String query, @PathVariable("limit") int limit) {
        return userService.getAllUsersByQuery(query, limit);
    }

    @GetMapping("users/auth/uid/{uid}")
    public ResponseEntity<AuthUser> getAuthUserByUid(@PathVariable("uid") String uid) {
        return userService.getAuthUserByUid(uid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users/uid/{uid}/follows")
    public List<User> getFollowsByUserUid(@PathVariable("uid") String userUid) {
        return userService.getFollowsByUserUid(userUid);
    }
}
