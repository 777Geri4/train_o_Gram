package com.five.train_o_gram.dto;

public class ImageDTO {

    private int id;
    private String path;

    public ImageDTO() {
    }

    public ImageDTO(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}