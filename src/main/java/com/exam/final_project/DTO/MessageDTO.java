package com.exam.final_project.DTO;
import java.time.LocalDateTime;

public class MessageDTO {
    private Long id;
    private String content;
    private LocalDateTime timestamp;
    private Long senderId;
    private String senderUsername;
    private Long recipientUserId;
    private Long recipientChannelId;

    public MessageDTO(Long id, String content, LocalDateTime timestamp, Long senderId, String senderUsername, Long recipientUserId, Long recipientChannelId) {
        this.id = id;
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.recipientUserId = recipientUserId;
        this.recipientChannelId = recipientChannelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Long getRecipientUserId() {
        return recipientUserId;
    }

    public void setRecipientUserId(Long recipientUserId) {
        this.recipientUserId = recipientUserId;
    }

    public Long getRecipientChannelId() {
        return recipientChannelId;
    }

    public void setRecipientChannelId(Long recipientChannelId) {
        this.recipientChannelId = recipientChannelId;
    }
}
