package com.five.train_o_gram.models.notifications;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.NotificationStatus;
import com.five.train_o_gram.util.NotificationType;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notification_comment")
public class NotificationComment extends Notification {
    @OneToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "id")
    private Comment comment;

    public NotificationComment() {
    }

    public NotificationComment(User recipient, User creator, Comment comment, NotificationType notificationType, NotificationStatus notificationStatus) {
        super(recipient, creator, notificationType, notificationStatus);
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
