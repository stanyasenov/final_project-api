package com.exam.final_project.service;

import com.exam.final_project.DTO.ChannelDTO;
import com.exam.final_project.helpers.ChannelRole;
import com.exam.final_project.model.Channel;
import com.exam.final_project.model.ChannelMember;
import com.exam.final_project.model.User;
import com.exam.final_project.repository.ChannelMemberRepository;
import com.exam.final_project.repository.ChannelRepository;
import com.exam.final_project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChannelMemberRepository channelMemberRepository;

    public Channel createChannel(String name, User owner) {
        Channel channel = new Channel();
        channel.setName(name);
        channel.setOwner(owner);
        Channel savedChannel = channelRepository.save(channel);
        joinChannel(owner.getId(), savedChannel.getId());

        return savedChannel;
    }

    public Optional<Channel> getChannelById(Long id) {
        return channelRepository.findById(id);
    }

    public void deleteOwnChannel(Long userId, Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        if (!channel.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("Only the owner can delete the channel");
        }

        channelRepository.delete(channel);
    }

    public void removeGuestFromChannel(Long ownerId, Long channelId, Long guestId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        if (!channel.getOwner().getId().equals(ownerId)) {
            throw new IllegalArgumentException("Only the owner can remove members from this channel");
        }

        ChannelMember guestMembership = channelMemberRepository.findByUserIdAndChannelId(guestId, channelId)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this channel"));

        if (!guestMembership.getRole().equals(ChannelRole.MEMBER)) {
            throw new IllegalArgumentException("Only users with the GUEST role can be removed");
        }

        channelMemberRepository.delete(guestMembership);
    }

    public List<ChannelMember> getChannelMembers(Long channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        return channelMemberRepository.findAllByChannel(channel);
    }

    public void joinChannel(Long userId, Long channelId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        if (channelMemberRepository.existsByUserAndChannel(user, channel)) {
            throw new IllegalArgumentException("User is already a member of the channel");
        }
        Set<Channel> ownedChannels = user.getOwnedChannels();

        ChannelMember member = new ChannelMember();
        member.setUser(user);
        member.setChannel(channel);

        if(ownedChannels.contains(channel)) {
            member.setRole(ChannelRole.OWNER);
        }
        else {
            member.setRole(channel.getDefaultRole());
        }

        channelMemberRepository.save(member);
    }

    public List<ChannelDTO> searchAllChannelsByName(String query) {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        return channelRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(ChannelDTO::new)
                .collect(Collectors.toList());
    }

    public void inviteUserToChannelByName(Long inviterId, Long channelId, String usernameToInvite) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new IllegalArgumentException("Channel not found"));

        User inviter = userRepository.findById(inviterId)
                .orElseThrow(() -> new IllegalArgumentException("Inviter not found"));

        User userToInvite = userRepository.findByUsername(usernameToInvite)
                .orElseThrow(() -> new IllegalArgumentException("User to invite not found"));

        boolean isAuthorized = channel.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(inviter) &&
                        (member.getRole() == ChannelRole.OWNER || member.getRole() == ChannelRole.ADMIN));

        if (!isAuthorized) {
            throw new IllegalArgumentException("Only owners or admins can invite users to this channel");
        }

        boolean isAlreadyMember = channel.getMembers().stream()
                .anyMatch(member -> member.getUser().equals(userToInvite));

        if (isAlreadyMember) {
            throw new IllegalArgumentException("User is already a member of the channel");
        }

        ChannelMember newMember = new ChannelMember();
        newMember.setChannel(channel);
        newMember.setUser(userToInvite);
        newMember.setRole(ChannelRole.MEMBER);

        channel.getMembers().add(newMember);
        channelRepository.save(channel);
    }

}
