package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserCommentNotificationDTO;
import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.dto.UserFollowersNotificationDTO;
import com.five.train_o_gram.dto.UserPostNotificationDTO;
import com.five.train_o_gram.models.notifications.Notification;
import com.five.train_o_gram.services.NotificationFactoryService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.util.NotificationType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationFactoryService notificationFactoryService;
    private final UserService userService;

    public NotificationController(NotificationFactoryService notificationFactoryService, UserService userService) {
        this.notificationFactoryService = notificationFactoryService;
        this.userService = userService;
    }

    @GetMapping("/subscribers")
    public ResponseEntity<UserFollowersNotificationDTO> findNotificationFriendship(){
        List<Notification> usersFriendshipNotificationList = notificationFactoryService
                .getFriendshipNotificationService()
                .findNotificationActivity(userService.getCurrentUser(), NotificationType.SUBSCRIBE);

        return ResponseEntity.ok(convertFromListOfFriendshipToUserFollowersDTO(usersFriendshipNotificationList));
    }

    @GetMapping("/comments")
    public ResponseEntity<UserCommentNotificationDTO> findNotificationComment(){
        List<Notification> usersCommentNotificationList = notificationFactoryService
                .getCommentNotificationService()
                .findNotificationActivity(userService.getCurrentUser(), NotificationType.LIKE_COMMENT);
        return ResponseEntity.ok(convertFromListOfLikesCommentsToLikesCommentDTO(usersCommentNotificationList));
    }

    @GetMapping("/posts")
    public ResponseEntity<UserPostNotificationDTO> findNotificationPost(){
        List<Notification> usersPostNotificationList = notificationFactoryService
                .getPostNotificationService()
                .findNotificationActivity(userService.getCurrentUser(), NotificationType.CREATE_POST);
        return ResponseEntity.ok(convertFromListOfPostNotificationToDTO(usersPostNotificationList));
    }

    private UserPostNotificationDTO convertFromListOfPostNotificationToDTO(List<Notification> usersPostNotificationList) {
        List<UserDTO> users = usersPostNotificationList.stream()
                .map(Notification::getCreator)
                .map(userService::convertUserToUserDTO)
                .toList();
        return new UserPostNotificationDTO(users.size(), users);
    }

    private UserFollowersNotificationDTO convertFromListOfFriendshipToUserFollowersDTO(List<Notification> usersFriendshipNotificationList){
        List<UserDTO> users = usersFriendshipNotificationList.stream()
                .map(Notification::getCreator)
                .map(userService::convertUserToUserDTO)
                .toList();
        return new UserFollowersNotificationDTO(users.size(), users);
    }

    private UserCommentNotificationDTO convertFromListOfLikesCommentsToLikesCommentDTO(List<Notification> likesCommentsNotificationList){
        List<UserDTO> users = likesCommentsNotificationList.stream()
                .map(Notification::getCreator)
                .map(userService::convertUserToUserDTO)
                .toList();
        return new UserCommentNotificationDTO(users.size(), users);
    }
}