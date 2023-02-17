package com.five.train_o_gram.services.impl.notification;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.notifications.Notification;
import com.five.train_o_gram.models.notifications.NotificationFriendship;
import com.five.train_o_gram.repositories.NotificationFriendshipRepository;
import com.five.train_o_gram.services.NotificationService;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class FriendshipNotificationServiceImpl implements NotificationService {
    private final NotificationFriendshipRepository notificationFriendshipRepository;

    @Autowired
    public FriendshipNotificationServiceImpl(NotificationFriendshipRepository notificationFriendshipRepository) {
        this.notificationFriendshipRepository = notificationFriendshipRepository;
    }

    @Override
    public void createNotification(User recipient, User creator, NotificationType notificationType,
                                   Object entityForNotification) {
        NotificationFriendship notificationFriendship = notificationFriendshipRepository
                .findByRecipientAndCreatorAndNotificationTypeAndNotificationStatus(recipient, creator, notificationType,
                        NotificationStatus.ACTIVE);

        if (notificationFriendship == null) {
            notificationFriendship = new NotificationFriendship(recipient, creator, notificationType,
                    NotificationStatus.ACTIVE);
            notificationFriendshipRepository.save(notificationFriendship);
        }
    }

    @Override
    public List<Notification> findNotificationActivity(User recipient, NotificationType notificationType) {
        List<NotificationFriendship> notificationFriendshipList = notificationFriendshipRepository
                .findAllByRecipientAndNotificationTypeAndNotificationStatus(recipient, notificationType,
                        NotificationStatus.ACTIVE);

        if (notificationFriendshipList.isEmpty()) {
            return Collections.emptyList();
        }

        notificationFriendshipList.forEach(notification -> notification.setNotificationStatus(NotificationStatus.SEEN));
        notificationFriendshipRepository.saveAll(notificationFriendshipList);
        return new ArrayList<>(notificationFriendshipList);
    }
}