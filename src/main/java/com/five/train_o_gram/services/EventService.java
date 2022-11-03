package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.event.EventFriendship;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.EventType;

import java.util.List;

public interface EventService {
    void createEventFriendshipActivity(User friend, User user);
    void createEventCommentActivity(User user, Comment comment);
    void createEventPostActivity(User user, Post post);
    List<EventFriendship> findEventFriendshipActivity(User user, EventType eventType);
}