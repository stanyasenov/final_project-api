package com.exam.final_project.repository;

import com.exam.final_project.model.Channel;
import com.exam.final_project.model.ChannelMember;
import com.exam.final_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelMemberRepository extends JpaRepository<ChannelMember, Long> {

    boolean existsByUserAndChannel(User user, Channel channel);

    Optional<ChannelMember> findByUserIdAndChannelId(Long userId, Long channelId);

    List<ChannelMember> findAllByChannel(Channel channel);
}
