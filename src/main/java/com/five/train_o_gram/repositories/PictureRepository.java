package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository <Picture, Integer> {
}