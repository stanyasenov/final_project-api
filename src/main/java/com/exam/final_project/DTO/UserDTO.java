package com.exam.final_project.DTO;

import com.exam.final_project.model.Channel;
import com.exam.final_project.model.ChannelMember;
import com.exam.final_project.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private Long id;
    private String username;
    private List<UserDTO> friends;
    private List<ChannelDTO> channels;
    private List<ChannelDTO> ownedChannels;

    public UserDTO(User user, boolean includeRelations) {
        this.id = user.getId();
        this.username = user.getUsername();

        if (includeRelations) {
            this.friends = user.getFriends().stream()
                    .map(friend -> new UserDTO(friend, false))
                    .collect(Collectors.toList());

            this.channels = user.getChannelMemberships().stream()
                    .map(ChannelMember::getChannel)
                    .map(ChannelDTO::new)
                    .collect(Collectors.toList());

            this.ownedChannels = user.getOwnedChannels().stream()
                    .map(ChannelDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<UserDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<UserDTO> friends) {
        this.friends = friends;
    }

    public List<ChannelDTO> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelDTO> channels) {
        this.channels = channels;
    }

    public List<ChannelDTO> getOwnedChannels() {
        return ownedChannels;
    }

    public void setOwnedChannels(List<ChannelDTO> ownedChannels) {
        this.ownedChannels = ownedChannels;
    }
}


