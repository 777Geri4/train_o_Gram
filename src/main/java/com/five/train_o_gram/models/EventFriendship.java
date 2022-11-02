package com.five.train_o_gram.models;

import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;

import javax.persistence.*;

@Entity
@Table(name = "event_friendship")
public class EventFriendship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;
    @Column(name = "event_type")
    @Enumerated
    private EventType eventType;

    @Column(name = "event_status")
    @Enumerated
    private EventStatus eventStatus;

    public EventFriendship() {
    }

    public EventFriendship(User owner, User creator, EventType eventType, EventStatus eventStatus) {
        this.owner = owner;
        this.creator = creator;
        this.eventType = eventType;
        this.eventStatus = eventStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return owner;
    }

    public void setUser(User user) {
        this.owner = user;
    }

    public User getFriend() {
        return creator;
    }

    public void setFriend(User friend) {
        this.creator = friend;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}