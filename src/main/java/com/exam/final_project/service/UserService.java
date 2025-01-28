package com.exam.final_project.service;

import com.exam.final_project.DTO.UserDTO;
import com.exam.final_project.model.Channel;
import com.exam.final_project.model.User;
import com.exam.final_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        return userRepository.save(user);
    }

    public boolean areFriends(User user1, User user2) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        return user1.getFriends().contains(user2);
    }

    public boolean isChannelMember(User user, Channel channel) {
        if (user == null || channel == null) {
            throw new IllegalArgumentException("User and Channel cannot be null");
        }
        return channel.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(user));
    }

    public Optional<User> getUserByIdEntity(Long id) {
        return userRepository.findById(id);
    }

    public void addFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        if (user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Users are already friends");
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);
    }

    public List<UserDTO> searchUsers(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        return userRepository.findByUsernameContainingIgnoreCase(query)
                .stream()
                .map(user -> new UserDTO(user, false))
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserSummary(Long userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDTO(user, true));
    }

    public void removeFriend(Long userId, Long friendId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        if (!user.getFriends().contains(friend)) {
            throw new IllegalArgumentException("The specified user is not a friend");
        }


        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);
    }
}
