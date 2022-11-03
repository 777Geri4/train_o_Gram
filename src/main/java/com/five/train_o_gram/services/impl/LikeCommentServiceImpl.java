package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.LikeComment;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.LikeCommentRepository;
import com.five.train_o_gram.services.*;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikeCommentServiceImpl implements LikeService {
    private final LikeCommentRepository likeCommentRepository;
    private final UserService userService;
    private final CommentService commentService;
    private final NotificationFactoryService notificationFactoryService;

    @Autowired
    public LikeCommentServiceImpl(LikeCommentRepository likeCommentRepository, UserService userService,
                                  CommentService commentService, NotificationFactoryService notificationFactoryService) {
        this.likeCommentRepository = likeCommentRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.notificationFactoryService = notificationFactoryService;
    }

    public List<User> getUserLikesFromComment (int commentId){
        return likeCommentRepository.findByCommentId(commentId).stream().map(LikeComment::getOwner).toList();
    }

    @Override
    @Transactional
    public void save(int commentId) {
        Comment comment = commentService.findComment(commentId);
        User currentUser = userService.getCurrentUser();
        int likesQuantity = comment.getLikes();
        comment.setLikes(++likesQuantity);
        likeCommentRepository.save(new LikeComment(currentUser, commentService.updateComment(comment)));
        notificationFactoryService
                .getCommentNotificationService()
                .createNotification(comment.getOwner(), currentUser, NotificationType.LIKE_COMMENT, comment);

    }

    @Override
    @Transactional
    public void delete(int commentId) {
        Comment comment = commentService.findComment(commentId);
        int likesQuantity = comment.getLikes();
        comment.setLikes(--likesQuantity);
        likeCommentRepository.delete(new LikeComment(userService.getCurrentUser(), commentService.updateComment(comment)));
    }
}