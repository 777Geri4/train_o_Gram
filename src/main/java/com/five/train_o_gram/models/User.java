package com.five.train_o_gram.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "User")
public class User{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Avatar> avatars;

    @OneToMany(mappedBy = "owner")
    private List<Post> posts;

    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;

    @OneToMany(mappedBy = "friend")
    private List<Relationship> friends;

    @OneToMany(mappedBy = "user")
    private List<Relationship> subscribers;

    @OneToMany(mappedBy = "owner")
    private List<LikeComment> likeComments;

    @OneToMany(mappedBy = "owner")
    private List<LikePicture> likePictures;

    @OneToMany(mappedBy = "owner")
    private List<LikePost> likePosts;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Relationship> getFriends() {
        return friends;
    }

    public void setFriends(List<Relationship> friends) {
        this.friends = friends;
    }

    public List<Relationship> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Relationship> friendOf) {
        this.subscribers = friendOf;
    }

    public List<LikeComment> getLikeComments() {
        return likeComments;
    }

    public void setLikeComments(List<LikeComment> likeComments) {
        this.likeComments = likeComments;
    }

    public List<LikePicture> getLikePictures() {
        return likePictures;
    }

    public void setLikePictures(List<LikePicture> likePictures) {
        this.likePictures = likePictures;
    }

    public List<LikePost> getLikePosts() {
        return likePosts;
    }

    public void setLikePosts(List<LikePost> likePosts) {
        this.likePosts = likePosts;
    }
}
