package com.five.train_o_gram.repositories;

import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.util.SubscribeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelationshipsRepository extends JpaRepository<Relationship, Integer> {
    List<Relationship> findBySubscriberAndSubscribeStatus(User subscriber, SubscribeStatus subscribeStatus);
    Relationship findBySubscriberAndPublisher(User subscriber, User publisher);
}