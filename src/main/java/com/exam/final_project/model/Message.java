package com.exam.final_project.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGES")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_user_id", nullable = true)
    private User recipientUser;

    @ManyToOne
    @JoinColumn(name = "recipient_channel_id", nullable = true)
    private Channel recipientChannel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipientUser = recipientUser;
    }

    public Channel getRecipientChannel() {
        return recipientChannel;
    }

    public void setRecipientChannel(Channel recipientChannel) {
        this.recipientChannel = recipientChannel;
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

    @Column(nullable = false)
    private String content;

    private LocalDateTime timestamp = LocalDateTime.now();

}
