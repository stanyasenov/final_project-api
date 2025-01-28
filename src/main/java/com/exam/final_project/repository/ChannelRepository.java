package com.exam.final_project.repository;

import com.exam.final_project.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByName(String name);

    List<Channel> findByNameContainingIgnoreCase(String name);

}
