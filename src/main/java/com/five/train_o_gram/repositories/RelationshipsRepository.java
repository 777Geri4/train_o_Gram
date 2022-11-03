package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.RelationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationship, Integer> {
    List<Relationship> findUserFriendsByUserAndRelationStatus(User user, RelationStatus relationStatus);
    Relationship findByUserAndFriend(User user, User friend);
}