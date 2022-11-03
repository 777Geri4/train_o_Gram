package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.repositories.RelationshipsRepository;
import com.five.train_o_gram.services.EventService;
import com.five.train_o_gram.services.RelationshipsService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.util.RelationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelationshipsServiceImpl implements RelationshipsService {
    private final RelationshipsRepository relationshipsRepository;
    private final UserService userService;
    private final EventService eventService;
    @Autowired
    public RelationshipsServiceImpl(RelationshipsRepository relationshipsRepository, UserService userService,
                                    EventService eventService) {
        this.relationshipsRepository = relationshipsRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    @Override
    @Transactional
    public void addToFriends(int friendID){
        User user = userService.getCurrentUser();
        if (user.getId() == friendID) return;
        User friend = userService.findOne(friendID);

        Relationship relationship = relationshipsRepository.findByUserAndFriend(user, friend);
        if (relationship == null) {
            relationshipsRepository.save(new Relationship(user, friend, RelationStatus.FRIEND));
            relationshipsRepository.save(new Relationship(friend, user, RelationStatus.SUBSCRIBER));
        } else {
            relationship.setFriendStatus(RelationStatus.FRIEND);
            relationshipsRepository.save(relationship);
        }
        eventService.createEventFriendshipActivity(friend, user);
    }
    @Override
    @Transactional
    public void addToSubscribers(int subscriberID){
        User user = userService.getCurrentUser();
        User subscriber = userService.findOne(subscriberID);
        Relationship relationship = relationshipsRepository.findByUserAndFriend(user, subscriber);
        relationship.setFriendStatus(RelationStatus.SUBSCRIBER);
        relationshipsRepository.save(relationship);
    }
    @Override
    public List<Relationship> getRelationshipByStatus(RelationStatus relationStatus){
        return relationshipsRepository.findUserFriendsByUserAndRelationStatus(userService.getCurrentUser(),
                relationStatus);
    }
}