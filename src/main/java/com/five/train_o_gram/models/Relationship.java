package com.five.train_o_gram.models;

import com.five.train_o_gram.util.RelationStatus;

import javax.persistence.*;

@Entity
@Table(name = "user_has_friends")
public class Relationship {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private User friend;

    @Column(name = "friend_status")
    @Enumerated
    private RelationStatus relationStatus;

    public Relationship(User user, User friend, RelationStatus relationStatus) {
        this.user = user;
        this.friend = friend;
        this.relationStatus = relationStatus;
    }

    public Relationship() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public RelationStatus getFriendStatus() {
        return relationStatus;
    }

    public void setFriendStatus(RelationStatus relationStatus) {
        this.relationStatus = relationStatus;
    }
}
