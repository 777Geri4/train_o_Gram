package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.dto.CommentDTO;
import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.repositories.CommentRepository;
import com.five.train_o_gram.services.CommentService;
import com.five.train_o_gram.util.exceptions.comment.CommentNotFoundExeption;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userServiceImpl, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public void create(Post post, String comment){
        commentRepository.save(new Comment(comment, new Date(),
                post, userServiceImpl.getCurrentUser()));
    }

    public List<Comment> findByPost(Post post){
        return commentRepository.findCommentsByPost(post);
    }

    public Comment findComment(int id){
        return commentRepository.findById(id).orElseThrow(CommentNotFoundExeption::new);
    }

    @Transactional
    public void deleteComment(int id){
        commentRepository.delete(findComment(id));
    }

    @Transactional
    public Comment updateComment(int id, String text){
        Comment comment = findComment(id);
        comment.setText(text);
        return commentRepository.save(comment);
    }
    @Transactional
    public Comment updateComment(Comment comment){
        return commentRepository.save(comment);
    }

    public CommentDTO convertCommentToCommentDTO(Comment comment){
        return modelMapper.map(comment, CommentDTO.class);
    }
}