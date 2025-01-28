package com.exam.final_project.repository;

import com.exam.final_project.model.Channel;
import com.exam.final_project.model.Message;
import com.exam.final_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderAndRecipientUser(User sender, User recipientUser);

    List<Message> findByRecipientChannel(Channel recipientChannel);

    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender = :user1 AND m.recipientUser = :user2) OR " +
            "(m.sender = :user2 AND m.recipientUser = :user1) " +
            "ORDER BY m.timestamp")
    List<Message> findChatMessages(@Param("user1") User user1, @Param("user2") User user2);
}

