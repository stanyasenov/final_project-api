package com.exam.final_project.DTO;

import com.exam.final_project.model.ChannelMember;

public class ChannelMemberDTO {
    private Long id;
    private Long userId;
    private String role;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ChannelMemberDTO(ChannelMember member) {
        this.id = member.getId();
        this.userId = member.getUser().getId();
        this.role = member.getRole().name();
        this.userName = member.getUser().getUsername();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}