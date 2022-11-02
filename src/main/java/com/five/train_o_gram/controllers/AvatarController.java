package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.ImageDTO;
import com.five.train_o_gram.services.AvatarService;
import com.five.train_o_gram.services.ImageService;
import com.five.train_o_gram.services.UserService;
import com.five.train_o_gram.services.impl.UserServiceImpl;
import com.five.train_o_gram.util.exceptions.ErrorResponse;
import com.five.train_o_gram.util.exceptions.avatar.AvatarNotCreatedException;
import com.five.train_o_gram.util.exceptions.avatar.AvatarNotDeletedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {
    public static final String AVATAR_NOT_DOWNLOADED = "Аватар не завантажено";
    private static final String AVATAR_NOT_DELETED = "Аватар не видалено";
    private final UserService userService;
    private final AvatarService avatarService;
    private final ImageService imageService;

    @Autowired
    public AvatarController(UserService userService, AvatarService avatarService, ImageService imageService) {
        this.userService = userService;
        this.avatarService = avatarService;
        this.imageService = imageService;
    }

    @GetMapping()
    public ResponseEntity<List<ImageDTO>> getAvatarsId(){
            return ResponseEntity.ok(avatarService.getUserAvatars(userService.getCurrentUser()).stream()
                    .map(imageService::convertSpecificImageToImageDTO).toList());
     }

    @GetMapping(("/user/{id}"))
    public ResponseEntity<List<ImageDTO>> getAvatarsByUserId(@PathVariable("id") int id){
        return ResponseEntity.ok(avatarService.getUserAvatars(userService.findOne(id)).stream()
                .map(imageService::convertSpecificImageToImageDTO).toList());
    }

    @GetMapping(("/{id}"))
    public ResponseEntity<byte[]> getAvatarView(@PathVariable("id") int id){
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(avatarService.showAvatar(id));
        } catch (IOException e) {
            throw new AvatarNotCreatedException();
        }
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<HttpStatus> uploadImage(@RequestBody MultipartFile image){
        try {
            avatarService.createAvatar(userService.getCurrentUser(), image);
        } catch (IOException e) {
            throw new AvatarNotCreatedException();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAvatar(@PathVariable("id") int id){
        if (avatarService.getUserAvatars(userService.getCurrentUser()).stream()
                .noneMatch(avatar -> avatar.getId() == id)) throw new AvatarNotDeletedException();
        avatarService.deleteAvatar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AvatarNotCreatedException avatarNotCreatedException){
        ErrorResponse response = new ErrorResponse(AVATAR_NOT_DOWNLOADED,
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AvatarNotDeletedException avatarNotDeletedException){
        ErrorResponse response = new ErrorResponse(AVATAR_NOT_DELETED,
                System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}