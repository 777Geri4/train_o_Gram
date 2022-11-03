package com.five.train_o_gram.models.notifications;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;

import javax.persistence.*;

@MappedSuperclass
public class Notification {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipient;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private User creator;
    @Column(name = "notification_type")
    @Enumerated
    private NotificationType notificationType;
    @Column(name = "notification_status")

    @Enumerated
    private NotificationStatus notificationStatus;
    public Notification() {
    }
    public Notification(User recipient, User creator, NotificationType notificationType, NotificationStatus notificationStatus) {
        this.recipient = recipient;
        this.creator = creator;
        this.notificationType = notificationType;
        this.notificationStatus = notificationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User user) {
        this.recipient = user;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User friend) {
        this.creator = friend;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }
    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
