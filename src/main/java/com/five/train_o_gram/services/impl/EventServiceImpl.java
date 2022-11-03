package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.*;
import com.five.train_o_gram.models.event.Event;
import com.five.train_o_gram.models.event.EventFriendship;
import com.five.train_o_gram.repositories.EventFriendshipRepository;
import com.five.train_o_gram.services.EventService;
import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventFriendshipRepository eventFriendshipRepository;

    @Autowired
    public EventServiceImpl(EventFriendshipRepository eventFriendshipRepository) {
        this.eventFriendshipRepository = eventFriendshipRepository;
    }
    @Override
    @Transactional
    public void createEventFriendshipActivity(User friend, User user){
        EventFriendship eventFriendship = eventFriendshipRepository
                .findByOwnerAndCreatorAndEventTypeAndEventStatus(friend, user, EventType.ADD_TO_FRIEND, EventStatus.ACTIVE);
        if (eventFriendship == null) {
            EventFriendship eventFriend = new EventFriendship(friend, user, EventType.ADD_TO_FRIEND, EventStatus.ACTIVE);
            eventFriendshipRepository.save(eventFriend);
        }
    }

    @Override
    public void createEventCommentActivity(User user, Comment comment) {

    }

    @Override
    public void createEventPostActivity(User user, Post post) {

    }

    @Override
    @Transactional
    public List<EventFriendship> findEventFriendshipActivity(User user, EventType eventType) {
        List<EventFriendship> eventFriendshipList = eventFriendshipRepository
                .findAllByOwnerAndEventTypeAndEventStatus(user, eventType, EventStatus.ACTIVE);
        if (eventFriendshipList == null) {
            return Collections.emptyList();
        } else {
            changeEventStatus(eventFriendshipList, EventStatus.SEEN);
        }
        return eventFriendshipList;
    }

    private void changeEventStatus(List<? extends Event> events, EventStatus eventStatus){
        events.forEach(event -> event.setEventStatus(eventStatus));
        events.forEach(event -> eventFriendshipRepository.save((EventFriendship)event));
    }
}