package com.five.train_o_gram.models;

import com.five.train_o_gram.util.SubscribeStatus;

import javax.persistence.*;

@Entity
@Table(name = "user_has_subscribers")
public class Relationship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_id", referencedColumnName = "id")
    private User subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private User publisher;

    @Column(name = "status_of_subscribe")
    @Enumerated()
    private SubscribeStatus subscribeStatus;

    public Relationship(User subscriber, User publisher, SubscribeStatus subscribeStatus) {
        this.subscriber = subscriber;
        this.publisher = publisher;
        this.subscribeStatus = subscribeStatus;
    }

    public Relationship() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public SubscribeStatus getSubscribeStatus() {
        return subscribeStatus;
    }

    public void setSubscribeStatus(SubscribeStatus subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }
}
