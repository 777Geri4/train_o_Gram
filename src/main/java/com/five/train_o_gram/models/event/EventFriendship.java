package com.five.train_o_gram.models.event;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;

import javax.persistence.*;

@Entity
@Table(name = "event_friendship")
public class EventFriendship extends Event {
    public EventFriendship() {
    }

    public EventFriendship(User owner, User creator, EventType eventType, EventStatus eventStatus) {
        super(owner, creator, eventType, eventStatus);
    }
}