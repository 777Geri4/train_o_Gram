package com.five.train_o_gram.dto;

public class CommentDTO {

    private int id;
    private String text;
    private int ownerID;
    private int likes;

    public CommentDTO() {
    }

    public CommentDTO(int id, int ownerID, String text) {
        this.id = id;
        this.ownerID = ownerID;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
