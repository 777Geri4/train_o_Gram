package com.five.train_o_gram.services;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.notifications.Notification;
import com.five.train_o_gram.util.NotificationType;

import java.util.List;

public interface NotificationService {
    void createNotification(User recipient, User creator, NotificationType notificationType, Object entityForNotification);
    List<Notification> findNotificationActivity(User recipient, NotificationType notificationType);
}