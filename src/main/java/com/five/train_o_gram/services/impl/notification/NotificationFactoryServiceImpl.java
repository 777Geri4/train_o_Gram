package com.five.train_o_gram.services.impl.notification;

import com.five.train_o_gram.services.NotificationFactoryService;
import com.five.train_o_gram.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationFactoryServiceImpl implements NotificationFactoryService {
    private final NotificationService friendshipNotificationService;
    private final NotificationService commentNotificationService;
    private final NotificationService postNotificationService;

    @Autowired
    public NotificationFactoryServiceImpl(@Qualifier("friendshipNotificationServiceImpl")
                                          NotificationService friendshipNotificationService,
                                          @Qualifier("commentNotificationServiceImpl")
                                          NotificationService commentNotificationService,
                                          @Qualifier("postNotificationServiceImpl")
                                          NotificationService postNotificationService) {
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
