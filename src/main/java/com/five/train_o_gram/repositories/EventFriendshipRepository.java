package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.event.EventFriendship;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventFriendshipRepository extends JpaRepository<EventFriendship, Integer> {
    EventFriendship findByOwnerAndCreatorAndEventTypeAndEventStatus(User owner, User creator, EventType eventType, EventStatus eventStatus);
    List<EventFriendship> findAllByOwnerAndEventTypeAndEventStatus(User owner, EventType eventType, EventStatus eventStatus);
}