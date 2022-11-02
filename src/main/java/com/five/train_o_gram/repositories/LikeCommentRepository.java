package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Integer> {
    List<LikeComment> findByCommentId (int commentId);
}