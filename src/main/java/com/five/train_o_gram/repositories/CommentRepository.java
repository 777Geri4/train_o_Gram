package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findCommentsByPost(Post post);
}