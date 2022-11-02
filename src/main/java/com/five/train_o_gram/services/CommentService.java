package com.five.train_o_gram.services;

import com.five.train_o_gram.dto.CommentDTO;
import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.Post;

import java.util.List;

public interface CommentService {
    void create (Post post, String comment);
    List<Comment> findByPost(Post post);
    Comment findComment(int id);
    void deleteComment(int id);
    Comment updateComment(int id, String text);
    Comment updateComment(Comment comment);
    CommentDTO convertCommentToCommentDTO(Comment comment);

}
