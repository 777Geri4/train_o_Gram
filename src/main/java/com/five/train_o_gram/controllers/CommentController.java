package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.CommentDTO;
import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.services.CommentService;
import com.five.train_o_gram.services.PostService;
import com.five.train_o_gram.services.impl.CommentServiceImpl;
import com.five.train_o_gram.services.impl.LikeCommentServiceImpl;
import com.five.train_o_gram.services.impl.PostServiceImpl;
import com.five.train_o_gram.services.impl.UserServiceImpl;
import com.five.train_o_gram.util.exceptions.comment.CommentNotFoundExeption;
import com.five.train_o_gram.util.exceptions.ErrorResponse;
import com.five.train_o_gram.util.exceptions.post.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    public static final String POST_NOT_FOUND = "Пост не знайдено";
    public static final String COMMENT_NOT_FOUND = "Комментарій не знайдено";
    private final CommentService commentService;
    private final PostService postService;
    private final LikeCommentServiceImpl likeCommentServiceImpl;
    private final UserServiceImpl userService;

    @Autowired
    public CommentController(CommentServiceImpl commentService, PostService postService,
                             LikeCommentServiceImpl likeCommentServiceImpl, UserServiceImpl userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.likeCommentServiceImpl = likeCommentServiceImpl;
        this.userService = userService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDTO>> findPostComments(@PathVariable("id") int id){
        return ResponseEntity.ok(commentService.findByPost(postService.findPostByID(id)).stream()
                .map(commentService::convertCommentToCommentDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findComment(@PathVariable("id") int id){
        return ResponseEntity.ok(commentService.convertCommentToCommentDTO(commentService.findComment(id)));
    }

    @PostMapping("post/{id}")
    public ResponseEntity<HttpStatus> createComment(@PathVariable("id") int id, String comment){
        commentService.create(postService.findPostByID(id), comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") int id, String text){
        return ResponseEntity.ok(commentService.convertCommentToCommentDTO(commentService.updateComment(id, text)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id){
        commentService.deleteComment(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<UserDTO>> getUsersDTOWhoLikeComment(@PathVariable("id") int id){
        return ResponseEntity.ok(likeCommentServiceImpl.getUserLikesFromComment(id).stream()
                .map(userService::convertUserToUserDTO).toList());
    }

    @PatchMapping("/{id}/likes")
    public ResponseEntity<HttpStatus> likeComment(@PathVariable("id") int id){
        likeCommentServiceImpl.save(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/likes")
    public ResponseEntity<HttpStatus> dislikeComment(@PathVariable("id") int id){
        likeCommentServiceImpl.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PostNotFoundException postNotFoundException){
        ErrorResponse response = new ErrorResponse(POST_NOT_FOUND, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(CommentNotFoundExeption commentNotFoundExeption){
        ErrorResponse response = new ErrorResponse(COMMENT_NOT_FOUND, System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}