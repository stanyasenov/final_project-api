package com.exam.final_project.model;

import com.exam.final_project.helpers.ChannelRole;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channels")
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChannelMember> members = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private ChannelRole defaultRole = ChannelRole.MEMBER;

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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<ChannelMember> getMembers() {
        return members;
    }

    public void setMembers(Set<ChannelMember> members) {
        this.members = members;
    }

    public ChannelRole getDefaultRole() {
        return defaultRole;
    }

    public void setDefaultRole(ChannelRole defaultRole) {
        this.defaultRole = defaultRole;
    }
}