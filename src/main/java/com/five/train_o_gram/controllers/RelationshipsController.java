package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.models.Relationship;
import com.five.train_o_gram.services.impl.RelationshipsServiceImpl;
import com.five.train_o_gram.services.impl.UserServiceImpl;
import com.five.train_o_gram.util.RelationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class RelationshipsController {
    private final RelationshipsServiceImpl relationshipsServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public RelationshipsController(RelationshipsServiceImpl relationshipsServiceImpl, UserServiceImpl userServiceImpl) {
        this.relationshipsServiceImpl = relationshipsServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUserFriends(){
        return ResponseEntity.ok(relationshipsServiceImpl.getRelationshipByStatus(RelationStatus.FRIEND).stream()
                .map(Relationship::getFriend)
                .map(userServiceImpl::convertUserToUserDTO).toList());
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<UserDTO>> getUserSubscribers(){
        return ResponseEntity.ok(relationshipsServiceImpl.getRelationshipByStatus(RelationStatus.SUBSCRIBER).stream()
                .map(Relationship::getFriend)
                .map(userServiceImpl::convertUserToUserDTO).toList());
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<HttpStatus> addFriend(@PathVariable("id") int id){
        relationshipsServiceImpl.addToFriends(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/add/{id}")
    public ResponseEntity<HttpStatus> addToSubscribers(@PathVariable("id") int id){
        relationshipsServiceImpl.addToSubscribers(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}