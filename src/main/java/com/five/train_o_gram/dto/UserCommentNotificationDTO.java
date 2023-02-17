package com.five.train_o_gram.dto;

import java.util.List;

public class UserCommentNotificationDTO {
    private int likesQuantity;
    private List <UserDTO> likesOwners;

    public UserCommentNotificationDTO() {
    }

    public UserCommentNotificationDTO(int likesQuantity, List<UserDTO> likesOwners) {
        this.likesQuantity = likesQuantity;
        this.likesOwners = likesOwners;
    }

    public int getLikesQuantity() {
        return likesQuantity;
    }

    public void setLikesQuantity(int likesQuantity) {
        this.likesQuantity = likesQuantity;
    }

    public List<UserDTO> getLikesOwners() {
        return likesOwners;
    }

    public void setLikesOwners(List<UserDTO> likesOwners) {
        this.likesOwners = likesOwners;
    }
}
