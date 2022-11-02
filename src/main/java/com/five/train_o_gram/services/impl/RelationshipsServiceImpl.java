package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.repositories.RelationshipsRepository;
import com.five.train_o_gram.services.RelationshipsService;
import com.five.train_o_gram.util.RelationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelationshipsServiceImpl implements RelationshipsService {
    private final RelationshipsRepository relationshipsRepository;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public RelationshipsServiceImpl(RelationshipsRepository relationshipsRepository, UserServiceImpl userServiceImpl) {
        this.relationshipsRepository = relationshipsRepository;
        this.userServiceImpl = userServiceImpl;
    }

    @Transactional
    public void addToFriends(int friendID){
        User user = userServiceImpl.getCurrentUser();
        User friend = userServiceImpl.findOne(friendID);
        Relationship relationship = relationshipsRepository.findByUserIdAndFriendId(user.getId(), friend.getId());
        if (relationship == null) {
            relationshipsRepository.save(new Relationship(user, friend, RelationStatus.FRIEND));
            relationshipsRepository.save(new Relationship(friend, user, RelationStatus.SUBSCRIBER));
        } else {
            relationship.setFriendStatus(RelationStatus.FRIEND);
            relationshipsRepository.save(relationship);
        }
    }

    @Transactional
    public void addToSubscribers(int subscriberID){
        User user = userServiceImpl.getCurrentUser();
        User subscriber = userServiceImpl.findOne(subscriberID);
        Relationship relationship = relationshipsRepository.findByUserIdAndFriendId(user.getId(), subscriber.getId());
        relationship.setFriendStatus(RelationStatus.SUBSCRIBER);
        relationshipsRepository.save(relationship);
    }

    public List<Relationship> getRelationshipByStatus(RelationStatus relationStatus){
        return relationshipsRepository.findUserFriendsByUserIdAndRelationStatus(userServiceImpl.getCurrentUser().getId(),
                relationStatus);
    }
}