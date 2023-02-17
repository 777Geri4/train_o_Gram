package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.services.RelationshipsService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.util.SubscribeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class RelationshipsController {
    private final RelationshipsService relationshipsService;
    private final UserService userService;

    @Autowired
    public RelationshipsController(RelationshipsService relationshipsService, UserService userService) {
        this.relationshipsService = relationshipsService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserPublishers(){
        return ResponseEntity.ok(relationshipsService.getRelationshipBySubscribeStatus(userService.getCurrentUser(),
                        SubscribeStatus.SUBSCRIBER).stream()
                .map(Relationship::getPublisher)
                .map(userService::convertUserToUserDTO).toList());
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<UserDTO>> getUserSubscribers(){
        return ResponseEntity.ok(relationshipsService.getRelationshipBySubscribeStatus(userService.getCurrentUser(),
                        SubscribeStatus.PUBLISHER).stream()
                .map(Relationship::getPublisher)
                .map(userService::convertUserToUserDTO).toList());
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<HttpStatus> subscribeTo(@PathVariable("id") int id){
        relationshipsService.subscribe(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/unsubscribe/{id}")
    public ResponseEntity<HttpStatus> subscribeFrom(@PathVariable("id") int id){
        relationshipsService.unsubscribe(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}