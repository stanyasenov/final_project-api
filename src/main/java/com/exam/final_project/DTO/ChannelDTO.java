package com.exam.final_project.DTO;

import com.exam.final_project.model.Channel;

import java.util.Set;
import java.util.stream.Collectors;

public class ChannelDTO {
    private Long id;
    private String name;
    private Long ownerId;
    private Set<Long> memberIds;

    public ChannelDTO(Channel channel) {
        this.id = channel.getId();
        this.name = channel.getName();
        this.ownerId = channel.getOwner().getId();
        this.memberIds = channel.getMembers().stream()
                .map(member -> member.getUser().getId())
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Set<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<Long> memberIds) {
        this.memberIds = memberIds;
    }
}