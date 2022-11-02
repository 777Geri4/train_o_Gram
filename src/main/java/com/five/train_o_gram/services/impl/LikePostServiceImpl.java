package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.LikePost;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.LikePostRepository;
import com.five.train_o_gram.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikePostServiceImpl implements LikeService {
    private final LikePostRepository likePostRepository;
    private final UserServiceImpl userService;
    private final PostServiceImpl postServiceImpl;

    @Autowired
    public LikePostServiceImpl(LikePostRepository likePostRepository, UserServiceImpl userService, PostServiceImpl postServiceImpl) {
        this.likePostRepository = likePostRepository;
        this.userService = userService;
        this.postServiceImpl = postServiceImpl;
    }

    public List<User> getUserLikesFromPost (int postId){
        return likePostRepository.findByPostId(postId).stream().map(LikePost::getOwner).toList();
    }

    @Override
    @Transactional
    public void save(int postId) {
        Post post = postServiceImpl.findPostByID(postId);
        int postLikes = post.getLikes();
        post.setLikes(++postLikes);
        likePostRepository.save(new LikePost(userService.getCurrentUser(), postServiceImpl.updatePost(post)));
    }

    @Override
    @Transactional
    public void delete(int postId) {
        Post post = postServiceImpl.findPostByID(postId);
        int postLikes = post.getLikes();
        post.setLikes(--postLikes);
        likePostRepository.delete(new LikePost(userService.getCurrentUser(), postServiceImpl.updatePost(post)));
    }
}