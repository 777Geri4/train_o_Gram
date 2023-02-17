package com.five.train_o_gram.dto;

import java.util.List;

public class UserPostNotificationDTO {
    private int quantityOfPostNotification;
    private List<UserDTO> postOwners;

    public UserPostNotificationDTO() {
    }

    public UserPostNotificationDTO(int quantityOfPostNotification, List<UserDTO> postOwners) {
        this.quantityOfPostNotification = quantityOfPostNotification;
        this.postOwners = postOwners;
    }

    public int getQuantityOfPostNotification() {
        return quantityOfPostNotification;
    }

    public void setQuantityOfPostNotification(int quantityOfPostNotification) {
        this.quantityOfPostNotification = quantityOfPostNotification;
    }

    public List<UserDTO> getPostOwners() {
        return postOwners;
    }

    public void setPostOwners(List<UserDTO> postOwners) {
        this.postOwners = postOwners;
    }
}
