package com.five.train_o_gram.dto;

import java.util.List;

public class UserDTO {
    private int userID;
    private String login;
    private List<ImageDTO> avatars;

    public UserDTO() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public List<ImageDTO> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<ImageDTO> avatars) {
        this.avatars = avatars;
    }
}