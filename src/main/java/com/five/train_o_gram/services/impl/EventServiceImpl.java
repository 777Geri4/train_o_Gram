package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.EventFriendship;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.EventFriendshipRepository;
import com.five.train_o_gram.services.EventService;
import com.five.train_o_gram.util.EventStatus;
import com.five.train_o_gram.util.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createEventFriendshipActivity(User user, User friend){
        EventFriendship eventFriend = new EventFriendship(user, friend, EventType.ADD_TO_FRIEND, EventStatus.ACTIVE);
        eventFriendshipRepository.save(eventFriend);
    }

    @Override
    public void createEventCommentActivity(User user, Comment comment) {

    }

    @Override
    public void createEventPostActivity(User user, Post post) {

    }
}