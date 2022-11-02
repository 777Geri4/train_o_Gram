package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.PostDTO;
import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.services.impl.LikePostServiceImpl;
import com.five.train_o_gram.services.impl.PostServiceImpl;
import com.five.train_o_gram.services.impl.UserServiceImpl;
import com.five.train_o_gram.util.exceptions.ErrorResponse;
import com.five.train_o_gram.util.exceptions.post.PostNotCreatedException;
import com.five.train_o_gram.util.exceptions.post.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    public static final String POST_NOT_CREATED = "Пост не створено";
    public static final String POST_NOT_FOUND = "Пост не знайдено";
    private final PostServiceImpl postServiceImpl;
    private final UserServiceImpl userService;
    private final LikePostServiceImpl likePostServiceImpl;

    @Autowired
    public PostController(PostServiceImpl postServiceImpl, UserServiceImpl userService, LikePostServiceImpl likePostServiceImpl) {
        this.postServiceImpl = postServiceImpl;
        this.userService = userService;
        this.likePostServiceImpl = likePostServiceImpl;
    }

    @GetMapping()
    public ResponseEntity<List<PostDTO>> getPosts() {
        return ResponseEntity.ok(postServiceImpl.findAllUserPost(userService.getCurrentUser()).stream()
                .map(postServiceImpl::convertPostToPostDTO).toList());
    }

    @GetMapping("/recommendations")
    public ResponseEntity<List<PostDTO>> getRecommendedPosts() {
        return ResponseEntity.ok(postServiceImpl.findSuggestions(userService.getCurrentUser()).stream()
                .map(postServiceImpl::convertPostToPostDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("id") int id) {
        return ResponseEntity.ok(postServiceImpl.convertPostToPostDTO(postServiceImpl.findPostByID(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createPost(@RequestBody MultipartFile image, String message){
        try {
            postServiceImpl.createPost(userService.getCurrentUser(), image, message);
        } catch (IOException e) {
            throw new PostNotCreatedException();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePost(@PathVariable("id") int id){
        postServiceImpl.deletePost(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<UserDTO>> getUsersDTOWhoLikePost(@PathVariable("id") int id){
        return ResponseEntity.ok(likePostServiceImpl.getUserLikesFromPost(id).stream()
                .map(userService::convertUserToUserDTO).toList());
    }

    @PatchMapping("/{id}/likes")
    public ResponseEntity<HttpStatus> likePost(@PathVariable("id") int id){
        likePostServiceImpl.save(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<HttpStatus> dislikePost(@PathVariable("id") int id){
        likePostServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PostNotFoundException postNotFoundException){
        ErrorResponse response = new ErrorResponse(POST_NOT_FOUND, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PostNotCreatedException postNotCreatedException){
        ErrorResponse response = new ErrorResponse(POST_NOT_CREATED, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
