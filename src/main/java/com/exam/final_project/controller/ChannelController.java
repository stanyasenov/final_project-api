package com.exam.final_project.controller;

import com.exam.final_project.DTO.ChannelMemberDTO;
import com.exam.final_project.DTO.ChannelDTO;
import com.exam.final_project.model.Channel;
import com.exam.final_project.model.User;
import com.exam.final_project.service.ChannelService;
import com.exam.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;

    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ChannelDTO> createChannel(
            @RequestParam Long ownerId,
            @RequestParam String channelName) {
        User owner = userService.getUserByIdEntity(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        Channel createdChannel = channelService.createChannel(channelName, owner);

        return ResponseEntity.ok(new ChannelDTO(createdChannel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDTO> getChannelById(@PathVariable Long id) {
        return channelService.getChannelById(id)
                .map(channel -> ResponseEntity.ok(new ChannelDTO(channel)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ChannelDTO>> searchAllChannelsByName(@RequestParam String query) {
        List<ChannelDTO> channels = channelService.searchAllChannelsByName(query);
        return ResponseEntity.ok(channels);
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> deleteOwnChannel(
            @PathVariable Long channelId,
            @RequestParam Long userId) {
        channelService.deleteOwnChannel(userId, channelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{channelId}/members/{guestId}")
    public ResponseEntity<Void> removeGuestFromChannel(
            @PathVariable Long channelId,
            @PathVariable Long guestId,
            @RequestParam Long ownerId) {
        channelService.removeGuestFromChannel(ownerId, channelId, guestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{channelId}/members")
    public ResponseEntity<List<ChannelMemberDTO>> listChannelMembers(@PathVariable Long channelId) {
        List<ChannelMemberDTO> members = channelService.getChannelMembers(channelId).stream()
                .map(ChannelMemberDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(members);
    }

    @PostMapping("/{channelId}/join")
    public ResponseEntity<Void> joinChannel(
            @PathVariable Long channelId,
            @RequestParam Long userId) {
        channelService.joinChannel(userId, channelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<Void> inviteUserToChannelByName(
            @RequestParam Long inviterId,
            @RequestParam Long channelId,
            @RequestParam String usernameToInvite) {
        channelService.inviteUserToChannelByName(inviterId, channelId, usernameToInvite);
        return ResponseEntity.ok().build();
    }
}