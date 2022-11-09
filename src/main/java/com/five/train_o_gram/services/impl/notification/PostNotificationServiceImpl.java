package com.five.train_o_gram.services.impl.notification;

import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.notifications.Notification;
import com.five.train_o_gram.models.notifications.NotificationPost;
import com.five.train_o_gram.repositories.NotificationPostRepository;
import com.five.train_o_gram.services.NotificationService;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostNotificationServiceImpl implements NotificationService {
    private final NotificationPostRepository notificationPostRepository;

    @Autowired
    public PostNotificationServiceImpl(NotificationPostRepository notificationPostRepository) {
        this.notificationPostRepository = notificationPostRepository;
    }

    @Override
    public void createNotification(User recipient, User creator, NotificationType notificationType, Object entityForNotification) {
        if (notificationType.equals(NotificationType.CREATE_POST)) {
            createNotificationPostActivityForUserSubscribers((Post) entityForNotification);
        } else {
            NotificationPost notificationPost = notificationPostRepository
                    .findByRecipientAndCreatorAndNotificationTypeAndNotificationStatus(recipient, creator,
                            notificationType, NotificationStatus.ACTIVE);
            if (notificationPost == null) {
                notificationPost = new NotificationPost(recipient, creator, (Post) entityForNotification,
                        notificationType, NotificationStatus.ACTIVE);
                notificationPostRepository.save(notificationPost);
            }
        }
    }

    @Override
    public List<Notification> findNotificationActivity(User recipient, NotificationType notificationType) {
        List<NotificationPost> notificationPostList = notificationPostRepository
                .findAllByRecipientAndNotificationTypeAndNotificationStatus(recipient, notificationType, NotificationStatus.ACTIVE);
        if (notificationPostList.isEmpty()) {
            return Collections.emptyList();
        }

        notificationPostList.forEach(e -> e.setNotificationStatus(NotificationStatus.SEEN));
        notificationPostRepository.saveAll(notificationPostList);

        return new ArrayList<>(notificationPostList);
    }

    private void createNotificationPostActivityForUserSubscribers(Post post){
        User postOwner = post.getOwner();
        List<User> subscribers = postOwner.getSubscribers().stream().map(Relationship::getSubscriber).toList();
        List<NotificationPost> eventPosts = new ArrayList<>();
        subscribers.forEach(subscriber -> eventPosts
                .add(new NotificationPost(subscriber, postOwner, post, NotificationType.CREATE_POST, NotificationStatus.ACTIVE)));
        notificationPostRepository.saveAll(eventPosts);
    }
}