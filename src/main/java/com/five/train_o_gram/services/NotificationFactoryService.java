package com.five.train_o_gram.services;

public interface NotificationFactoryService {
    NotificationService getFriendshipNotificationService();
    NotificationService getCommentNotificationService();
    NotificationService getPostNotificationService();
}