package com.exam.final_project.service;
import com.exam.final_project.DTO.MessageDTO;
import com.exam.final_project.model.Channel;
import com.exam.final_project.model.Message;
import com.exam.final_project.model.User;
import com.exam.final_project.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    public MessageDTO sendPrivateMessage(User sender, User recipient, String content) {
        if (!userService.areFriends(sender, recipient)) {
            throw new IllegalArgumentException("You can only message your friends.");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setRecipientUser(recipient);
        message.setContent(content);
        Message savedMessage = messageRepository.save(message);

        return convertToDTO(savedMessage);
    }

    public MessageDTO sendChannelMessage(User sender, Channel channel, String content) {
        if (!userService.isChannelMember(sender, channel)) {
            throw new IllegalArgumentException("You can only message channels you are a member of.");
        }

        Message message = new Message();
        message.setSender(sender);
        message.setRecipientChannel(channel);
        message.setContent(content);
        Message savedMessage = messageRepository.save(message);

        return convertToDTO(savedMessage);
    }

    public List<MessageDTO> getPrivateMessages(User user1, User user2) {
        if (!userService.areFriends(user1, user2)) {
            throw new IllegalArgumentException("You can only view messages with your friends.");
        }

        return messageRepository.findChatMessages(user1, user2)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getChannelMessages(User user, Channel channel) {
        if (!userService.isChannelMember(user, channel)) {
            throw new IllegalArgumentException("You can only view messages in channels you are a member of.");
        }

        return messageRepository.findByRecipientChannel(channel)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert to DTO
    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
                message.getId(),
                message.getContent(),
                message.getTimestamp(),
                message.getSender().getId(),
                message.getSender().getUsername(),
                message.getRecipientUser() != null ? message.getRecipientUser().getId() : null,
                message.getRecipientChannel() != null ? message.getRecipientChannel().getId() : null
        );
    }
}
