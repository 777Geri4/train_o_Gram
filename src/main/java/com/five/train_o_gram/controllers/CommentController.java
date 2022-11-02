package com.five.train_o_gram.controllers;

import com.five.train_o_gram.dto.CommentDTO;
import com.five.train_o_gram.dto.UserDTO;
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
    private final CommentServiceImpl commentServiceImpl;
    private final PostServiceImpl postServiceImpl;
    private final LikeCommentServiceImpl likeCommentServiceImpl;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public CommentController(CommentServiceImpl commentServiceImpl, PostServiceImpl postServiceImpl,
                             LikeCommentServiceImpl likeCommentServiceImpl, UserServiceImpl userServiceImpl) {
        this.commentServiceImpl = commentServiceImpl;
        this.postServiceImpl = postServiceImpl;
        this.likeCommentServiceImpl = likeCommentServiceImpl;
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<List<CommentDTO>> findPostComments(@PathVariable("id") int id){
        return ResponseEntity.ok(commentServiceImpl.findByPost(postServiceImpl.findPostByID(id)).stream()
                .map(commentServiceImpl::convertCommentToCommentDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> findComment(@PathVariable("id") int id){
        return ResponseEntity.ok(commentServiceImpl.convertCommentToCommentDTO(commentServiceImpl.findComment(id)));
    }

    @PostMapping("post/{id}")
    public ResponseEntity<HttpStatus> createComment(@PathVariable("id") int id, String comment){
        commentServiceImpl.create(postServiceImpl.findPostByID(id), comment);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable("id") int id, String text){
        return ResponseEntity.ok(commentServiceImpl.convertCommentToCommentDTO(commentServiceImpl.updateComment(id, text)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("id") int id){
        commentServiceImpl.deleteComment(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<List<UserDTO>> getUsersDTOWhoLikeComment(@PathVariable("id") int id){
        return ResponseEntity.ok(likeCommentServiceImpl.getUserLikesFromComment(id).stream()
                .map(userServiceImpl::convertUserToUserDTO).toList());
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
