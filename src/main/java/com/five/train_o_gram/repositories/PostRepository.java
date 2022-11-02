package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository <Post, Integer> {
    List<Post> findAllIdByOwnerId(int id);
}