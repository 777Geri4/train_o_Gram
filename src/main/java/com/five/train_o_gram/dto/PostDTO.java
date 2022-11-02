package com.five.train_o_gram.dto;

import java.util.List;

public class PostDTO {
    private int id;
    private int likes;
    private String message;
    private int ownerID;
    private List<ImageDTO> pictures;
    private List<CommentDTO> comments;

    public PostDTO(int id, int likes, String message, int ownerID, List<ImageDTO> pictures, List<CommentDTO> comments) {
        this.id = id;
        this.likes = likes;
        this.message = message;
        this.ownerID = ownerID;
        this.pictures = pictures;
        this.comments = comments;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOwner() {
        return ownerID;
    }

    public void setOwner(int ownerID) {
        this.ownerID = ownerID;
    }

    public List<ImageDTO> getPictures() {
        return pictures;
    }

    public void setPictures(List<ImageDTO> pictures) {
        this.pictures = pictures;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}