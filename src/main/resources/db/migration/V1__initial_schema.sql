CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE channels (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) UNIQUE NOT NULL,
                          owner_id BIGINT NOT NULL,
                          FOREIGN KEY (owner_id) REFERENCES users (id)
);

CREATE TABLE channel_members (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 user_id BIGINT NOT NULL,
                                 channel_id BIGINT NOT NULL,
                                 role VARCHAR(255) NOT NULL,
                                 FOREIGN KEY (user_id) REFERENCES users (id),
                                 FOREIGN KEY (channel_id) REFERENCES channels (id)
);

CREATE TABLE user_friends (
                              user_id BIGINT NOT NULL,
                              friend_id BIGINT NOT NULL,
                              PRIMARY KEY (user_id, friend_id),
                              FOREIGN KEY (user_id) REFERENCES users (id),
                              FOREIGN KEY (friend_id) REFERENCES users (id)
);