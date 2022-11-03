package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.LikePost;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.LikePostRepository;
import com.five.train_o_gram.services.*;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikePostServiceImpl implements LikeService {
    private final LikePostRepository likePostRepository;
    private final UserService userService;
    private final PostService postService;
    private final NotificationFactoryService notificationFactoryService;

    @Autowired
    public LikePostServiceImpl(LikePostRepository likePostRepository, UserService userService, PostService postService,
                               NotificationFactoryService notificationFactoryService) {
        this.likePostRepository = likePostRepository;
        this.userService = userService;
        this.postService = postService;
        this.notificationFactoryService = notificationFactoryService;
    }

    public List<User> getUserLikesFromPost (int postId){
        return likePostRepository.findByPostId(postId).stream().map(LikePost::getOwner).toList();
    }

    @Override
    @Transactional
    public void save(int postId) {
        Post post = postService.findPostByID(postId);
        User currentUser = userService.getCurrentUser();
        int postLikes = post.getLikes();
        post.setLikes(++postLikes);
        likePostRepository.save(new LikePost(currentUser, postService.updatePost(post)));
        notificationFactoryService
                .getPostNotificationService()
                .createNotification(post.getOwner(), currentUser, NotificationType.LIKE_POST, post);
    }

    @Override
    @Transactional
    public void delete(int postId) {
        Post post = postService.findPostByID(postId);
        int postLikes = post.getLikes();
        post.setLikes(--postLikes);
        likePostRepository.delete(new LikePost(userService.getCurrentUser(), postService.updatePost(post)));
    }
}