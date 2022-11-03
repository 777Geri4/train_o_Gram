package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.dto.UserFollowersDTO;
import com.five.train_o_gram.models.event.Event;
import com.five.train_o_gram.models.event.EventFriendship;
import com.five.train_o_gram.services.EventService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.util.EventType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final EventService eventService;
    private final UserService userService;

    public NotificationController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @GetMapping("/friends")
    public ResponseEntity<UserFollowersDTO> findEventFriendship(){
        List<EventFriendship> usersFriendshipNotificationList = eventService.findEventFriendshipActivity(userService
                .getCurrentUser(), EventType.ADD_TO_FRIEND);
        return ResponseEntity.ok(convertFromListOfFriendshipToUserFollowersDTO(usersFriendshipNotificationList));
    }

    private UserFollowersDTO convertFromListOfFriendshipToUserFollowersDTO(List<EventFriendship> usersFriendshipNotificationList){
        List<UserDTO> users = usersFriendshipNotificationList.stream()
                .map(Event::getCreator)
                .map(userService::convertUserToUserDTO)
                .toList();
        return new UserFollowersDTO(users.size(), users);
    }
}
