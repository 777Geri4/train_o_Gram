package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.repositories.RelationshipsRepository;
import com.five.train_o_gram.services.NotificationService;
import com.five.train_o_gram.services.RelationshipsService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.util.NotificationType;
import com.five.train_o_gram.util.SubscribeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RelationshipsServiceImpl implements RelationshipsService {
    private final RelationshipsRepository relationshipsRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    @Autowired
    public RelationshipsServiceImpl(RelationshipsRepository relationshipsRepository, UserService userService,
                                    @Qualifier("friendshipNotificationServiceImpl") NotificationService notificationService) {
        this.relationshipsRepository = relationshipsRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public void subscribe(int publisherID){
        User subscriber = userService.getCurrentUser();
        if (subscriber.getId() == publisherID) return;
        User publisher = userService.findOne(publisherID);

        Relationship relationship = relationshipsRepository.findBySubscriberAndPublisher(subscriber, publisher);
        if (relationship == null) {
            relationshipsRepository.save(new Relationship(subscriber, publisher, SubscribeStatus.SUBSCRIBER));
            relationshipsRepository.save(new Relationship(publisher, subscriber, SubscribeStatus.PUBLISHER));
        } else {
            relationship.setSubscribeStatus(SubscribeStatus.SUBSCRIBER);
            relationshipsRepository.save(relationship);
        }
        notificationService.createNotification(publisher, subscriber, NotificationType.SUBSCRIBE, null);
    }
    @Override
    @Transactional
    public void unsubscribe(int publisherID){
        User subscriber = userService.getCurrentUser();
        User publisher = userService.findOne(publisherID);
        Relationship relationship = relationshipsRepository.findBySubscriberAndPublisher(subscriber, publisher);
        relationship.setSubscribeStatus(SubscribeStatus.PUBLISHER);
        relationshipsRepository.save(relationship);
    }
    @Override
    public List<Relationship> getRelationshipBySubscribeStatus(SubscribeStatus subscribeStatus){
        return relationshipsRepository.findBySubscriberAndSubscribeStatus(userService.getCurrentUser(),
                subscribeStatus);
    }
}