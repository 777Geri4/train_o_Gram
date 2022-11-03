package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.services.impl.RelationshipsServiceImpl;
import com.five.train_o_gram.services.impl.UserServiceImpl;
import com.five.train_o_gram.util.SubscribeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class RelationshipsController {
    private final RelationshipsServiceImpl relationshipsServiceImpl;
    private final UserServiceImpl userService;

    @Autowired
    public RelationshipsController(RelationshipsServiceImpl relationshipsServiceImpl, UserServiceImpl userService) {
        this.relationshipsServiceImpl = relationshipsServiceImpl;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserPublishers(){
        return ResponseEntity.ok(relationshipsServiceImpl.getRelationshipBySubscribeStatus(SubscribeStatus.SUBSCRIBER).stream()
                .map(Relationship::getPublisher)
                .map(userService::convertUserToUserDTO).toList());
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<UserDTO>> getUserSubscribers(){
        return ResponseEntity.ok(relationshipsServiceImpl.getRelationshipBySubscribeStatus(SubscribeStatus.PUBLISHER).stream()
                .map(Relationship::getPublisher)
                .map(userService::convertUserToUserDTO).toList());
    }

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<HttpStatus> subscribeTo(@PathVariable("id") int id){
        relationshipsServiceImpl.subscribe(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/unsubscribe/{id}")
    public ResponseEntity<HttpStatus> subscribeFrom(@PathVariable("id") int id){
        relationshipsServiceImpl.unsubscribe(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}