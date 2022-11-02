package com.five.train_o_gram.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table (name = "post")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "likes")
    private int likes;

    @Column(name = "created")
    private Date created;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "post")
    private List<Picture> pictures;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<LikePost> likePosts;

    public Post() {
    }

    public Post(Date created, User owner, String message) {
        this.created = created;
        this.owner = owner;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<LikePost> getLikeComments() {
        return likePosts;
    }

    public void setLikeComments(List<LikePost> likePosts) {
        this.likePosts = likePosts;
    }
}