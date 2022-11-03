package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.util.SubscribeStatus;

import java.util.List;

public interface RelationshipsService {
    void subscribe(int publisherID);
    void unsubscribe(int publisherID);
    List<Relationship> getRelationshipBySubscribeStatus(SubscribeStatus subscribeStatus);
}