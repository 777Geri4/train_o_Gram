package com.five.train_o_gram.models.notifications;

import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification_post")
public class NotificationPost extends Notification {
    @OneToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    public NotificationPost() {
    }

    public NotificationPost(User recipient, User creator, Post post, NotificationType notificationType, NotificationStatus notificationStatus) {
        super(recipient, creator, notificationType, notificationStatus);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
