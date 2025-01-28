package com.exam.final_project.controller;
import com.exam.final_project.DTO.MessageDTO;
import com.exam.final_project.DTO.MessageRequestDTO;
import com.exam.final_project.model.Channel;
import com.exam.final_project.model.Message;
import com.exam.final_project.model.User;
import com.exam.final_project.service.ChannelService;
import com.exam.final_project.service.MessageService;
import com.exam.final_project.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final ChannelService channelService;

    public MessageController(MessageService messageService, UserService userService, ChannelService channelService) {
        this.messageService = messageService;
        this.userService = userService;
        this.channelService = channelService;
    }

    @PostMapping("/private")
    public ResponseEntity<MessageDTO> sendPrivateMessage(@RequestBody MessageRequestDTO messageRequest) {
        Long senderId = messageRequest.getSenderId();
        Long recipientId = messageRequest.getRecipientId();
        String content = messageRequest.getContent();
        User sender = userService.getUserByIdEntity(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User recipient = userService.getUserByIdEntity(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        MessageDTO messageDTO = messageService.sendPrivateMessage(sender, recipient, content);
        return ResponseEntity.ok(messageDTO);
    }

    @PostMapping("/channel")
    public ResponseEntity<MessageDTO> sendChannelMessage(@RequestBody MessageRequestDTO messageRequest) {
        Long senderId = messageRequest.getSenderId();
        Long channelId = messageRequest.getRecipientId();
        String content = messageRequest.getContent();
        User sender = userService.getUserByIdEntity(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Channel channel = channelService.getChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        MessageDTO messageDTO = messageService.sendChannelMessage(sender, channel, content);
        return ResponseEntity.ok(messageDTO);
    }

    @GetMapping("/private")
    public ResponseEntity<List<MessageDTO>> getPrivateMessages(
            @RequestParam Long senderId,
            @RequestParam Long recipientId) {
        User user1 = userService.getUserByIdEntity(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User user2 = userService.getUserByIdEntity(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        List<MessageDTO> messages = messageService.getPrivateMessages(user1, user2);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/channel")
    public ResponseEntity<List<MessageDTO>> getChannelMessages(
            @RequestParam Long userId,
            @RequestParam Long channelId) {
        User user = userService.getUserByIdEntity(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Channel channel = channelService.getChannelById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        List<MessageDTO> messages = messageService.getChannelMessages(user, channel);
        return ResponseEntity.ok(messages);
    }
}


