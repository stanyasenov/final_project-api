CREATE TABLE messages (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          sender_id BIGINT NOT NULL,
                          recipient_user_id BIGINT,
                          recipient_channel_id BIGINT,
                          content VARCHAR(255) NOT NULL,
                          timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                          CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT fk_recipient_user FOREIGN KEY (recipient_user_id) REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT fk_recipient_channel FOREIGN KEY (recipient_channel_id) REFERENCES channels (id) ON DELETE CASCADE
);
