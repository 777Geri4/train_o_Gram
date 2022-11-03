package com.five.train_o_gram.services.impl.notification;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.notifications.Notification;
import com.five.train_o_gram.models.notifications.NotificationComment;
import com.five.train_o_gram.repositories.NotificationCommentRepository;
import com.five.train_o_gram.services.NotificationService;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CommentNotificationServiceImpl implements NotificationService {
    private final NotificationCommentRepository notificationCommentRepository;

    @Autowired
    public CommentNotificationServiceImpl(NotificationCommentRepository notificationCommentRepository) {
        this.notificationCommentRepository = notificationCommentRepository;
    }

    @Override
    public void createNotification(User recipient, User creator, NotificationType notificationType, Object entityForNotification) {
        NotificationComment notificationComment = notificationCommentRepository
                .findByRecipientAndCreatorAndNotificationTypeAndNotificationStatus
                        (recipient, creator, notificationType, NotificationStatus.ACTIVE);

        if (notificationComment == null) {
            notificationComment = new NotificationComment(recipient, creator, (Comment) entityForNotification,
                    notificationType, NotificationStatus.ACTIVE);
            notificationCommentRepository.save(notificationComment);
        }
    }

    @Override
    public List<Notification> findNotificationActivity(User recipient, NotificationType notificationType) {
        List<NotificationComment> notificationCommentList = notificationCommentRepository
                .findAllByRecipientAndNotificationTypeAndNotificationStatus(recipient, notificationType, NotificationStatus.ACTIVE);
        if (notificationCommentList == null) {
            return Collections.emptyList();
        }

        notificationCommentList.forEach(e -> e.setNotificationStatus(NotificationStatus.SEEN));
        notificationCommentRepository.saveAll(notificationCommentList);

        return List.of((Notification) notificationCommentList);
    }
}