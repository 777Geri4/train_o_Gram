package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.util.RelationStatus;

import java.util.List;

public interface RelationshipsService {
    void addToFriends(int friendID);
    void addToSubscribers(int subscriberID);
    List<Relationship> getRelationshipByStatus(RelationStatus relationStatus);
}