package com.five.train_o_gram.dto;

import java.util.List;

public class UserFollowersDTO {
    private int followersQuantity;
    private List<UserDTO> followers;

    public UserFollowersDTO() {
    }

    public UserFollowersDTO(int followersQuantity, List<UserDTO> followers) {
        this.followersQuantity = followersQuantity;
        this.followers = followers;
    }

    public int getFollowersQuantity() {
        return followersQuantity;
    }

    public void setFollowersQuantity(int followersQuantity) {
        this.followersQuantity = followersQuantity;
    }

    public List<UserDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDTO> followers) {
        this.followers = followers;
    }
}
