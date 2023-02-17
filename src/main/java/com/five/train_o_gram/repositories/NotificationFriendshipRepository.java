package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.notifications.NotificationFriendship;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationFriendshipRepository extends JpaRepository<NotificationFriendship, Integer> {
    NotificationFriendship findByRecipientAndCreatorAndNotificationTypeAndNotificationStatus
            (User recipient, User creator, NotificationType notificationType, NotificationStatus notificationStatus);
    List<NotificationFriendship> findAllByRecipientAndNotificationTypeAndNotificationStatus
            (User recipient, NotificationType notificationType, NotificationStatus notificationStatus);
}