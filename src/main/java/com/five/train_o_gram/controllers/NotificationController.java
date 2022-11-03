package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.dto.UserFollowersDTO;
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
    public ResponseEntity<UserFollowersDTO> findNotificationFriendship(){
        List<Notification> usersFriendshipNotificationList = notificationFactoryService
                .getFriendshipNotificationService()
                .findNotificationActivity(userService.getCurrentUser(), NotificationType.SUBSCRIBE);

        return ResponseEntity.ok(convertFromListOfFriendshipToUserFollowersDTO(usersFriendshipNotificationList));
    }
    @GetMapping("/comments")
    public ResponseEntity<?> findNotificationComment(){

        return ResponseEntity.ok("Ok");
    }

    private UserFollowersDTO convertFromListOfFriendshipToUserFollowersDTO(List<Notification> usersFriendshipNotificationList){
        List<UserDTO> users = usersFriendshipNotificationList.stream()
                .map(Notification::getCreator)
                .map(userService::convertUserToUserDTO)
                .toList();
        return new UserFollowersDTO(users.size(), users);
    }
}
