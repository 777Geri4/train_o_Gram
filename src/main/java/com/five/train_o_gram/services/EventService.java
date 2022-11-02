package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;

public interface EventService {
    void createEventFriendshipActivity(User user, User friend);
    void createEventCommentActivity(User user, Comment comment);
    void createEventPostActivity(User user, Post post);
}
