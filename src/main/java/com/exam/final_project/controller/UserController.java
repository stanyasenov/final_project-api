package com.exam.final_project.controller;

import com.exam.final_project.DTO.UserDTO;
import com.exam.final_project.model.User;
import com.exam.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserSummary(@PathVariable Long userId) {
        return userService.getUserSummary(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        List<UserDTO> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        userService.addFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        try {
            userService.removeFriend(userId, friendId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}