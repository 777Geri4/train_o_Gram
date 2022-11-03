package com.five.train_o_gram.models.event;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public class Event {
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
    public Event() {
    }
    public Event(User owner, User creator, EventType eventType, EventStatus eventStatus) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User friend) {
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
