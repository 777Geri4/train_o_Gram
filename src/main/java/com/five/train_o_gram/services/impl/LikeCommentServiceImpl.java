package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.LikeComment;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.LikeCommentRepository;
import com.five.train_o_gram.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikeCommentServiceImpl implements LikeService {
    private final LikeCommentRepository likeCommentRepository;
    private final UserServiceImpl userServiceImpl;
    private final CommentServiceImpl commentServiceImpl;

    @Autowired
    public LikeCommentServiceImpl(LikeCommentRepository likeCommentRepository, UserServiceImpl userServiceImpl,
                                  CommentServiceImpl commentServiceImpl) {
        this.likeCommentRepository = likeCommentRepository;
        this.userServiceImpl = userServiceImpl;
        this.commentServiceImpl = commentServiceImpl;
    }

    public List<User> getUserLikesFromComment (int commentId){
        return likeCommentRepository.findByCommentId(commentId).stream().map(LikeComment::getOwner).toList();
    }

    @Override
    @Transactional
    public void save(int commentId) {
        Comment comment = commentServiceImpl.findComment(commentId);
        int likesQuantity = comment.getLikes();
        comment.setLikes(++likesQuantity);
        likeCommentRepository.save(new LikeComment(userServiceImpl.getCurrentUser(), commentServiceImpl.updateComment(comment)));
    }

    @Override
    @Transactional
    public void delete(int commentId) {
        Comment comment = commentServiceImpl.findComment(commentId);
        int likesQuantity = comment.getLikes();
        comment.setLikes(--likesQuantity);
        likeCommentRepository.delete(new LikeComment(userServiceImpl.getCurrentUser(), commentServiceImpl.updateComment(comment)));
    }
}