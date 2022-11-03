package com.five.train_o_gram.models.notifications;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;

import javax.persistence.*;

@Entity
@Table(name = "notification_friendship")
public class NotificationFriendship extends Notification {
    public NotificationFriendship() {
    }

    public NotificationFriendship(User recipient, User creator, NotificationType notificationType, NotificationStatus notificationStatus) {
        super(recipient, creator, notificationType, notificationStatus);
    }
}