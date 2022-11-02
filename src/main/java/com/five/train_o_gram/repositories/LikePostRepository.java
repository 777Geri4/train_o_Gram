package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikePostRepository extends JpaRepository<LikePost, Integer> {
    List<LikePost> findByPostId(int postId);
}