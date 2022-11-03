package com.five.train_o_gram.services.impl.notification;

import com.five.train_o_gram.services.NotificationFactoryService;
import com.five.train_o_gram.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactoryServiceImpl implements NotificationFactoryService {
    private final FriendshipNotificationServiceImpl friendshipNotificationService;
    private final CommentNotificationServiceImpl commentNotificationService;
    private final PostNotificationServiceImpl postNotificationService;

    @Autowired
    public NotificationFactoryServiceImpl(FriendshipNotificationServiceImpl friendshipNotificationService,
                                          CommentNotificationServiceImpl commentNotificationService,
                                          PostNotificationServiceImpl postNotificationService) {
        this.friendshipNotificationService = friendshipNotificationService;
        this.commentNotificationService = commentNotificationService;
        this.postNotificationService = postNotificationService;
    }

    @Override
    public NotificationService getFriendshipNotificationService() {
        return friendshipNotificationService;
    }

    @Override
    public NotificationService getCommentNotificationService() {
        return commentNotificationService;
    }

    @Override
    public NotificationService getPostNotificationService() {
        return postNotificationService;
    }
}
