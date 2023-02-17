package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.dto.CommentDTO;
import com.five.train_o_gram.dto.ImageDTO;
import com.five.train_o_gram.dto.PostDTO;
import com.five.train_o_gram.models.Comment;
import com.five.train_o_gram.models.Picture;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.PostRepository;
import com.five.train_o_gram.services.*;
import com.five.train_o_gram.util.NotificationType;
import com.five.train_o_gram.util.exceptions.post.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final PictureService pictureService;
    private final CommentService commentService;
    private final NotificationService notificationService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ImageService imageService,
                           PictureService pictureService, CommentService commentService,
                           @Qualifier("postNotificationServiceImpl") NotificationService notificationService) {
        this.postRepository = postRepository;
        this.imageService = imageService;
        this.pictureService = pictureService;
        this.commentService = commentService;
        this.notificationService = notificationService;
    }

    public List<Post> findAllUserPost(User user){
        return postRepository.findAllIdByOwnerId(user.getId());
    }

    public Post findPostByID(int id){
        return postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional
    public void createPost(User user, MultipartFile image, String message) throws IOException {
        Post post = postRepository.save(new Post (new Date(), user, message));
        pictureService.createPicture(user, image, post);
        notificationService.createNotification(null, user, NotificationType.CREATE_POST, post);
    }

    @Transactional
    public void deletePost(int id){
        postRepository.delete(findPostByID(id));
    }

    @Transactional
    public Post updatePost(Post post){
        return postRepository.save(post);
    }

    public PostDTO convertPostToPostDTO(Post post){
        return new PostDTO(post.getId(), post.getLikes(), post.getMessage(), post.getOwner().getId(),
                convertListOfPicturesToListOfPicturesDTO(post.getPictures()),
                convertListOfCommentsToListOfCommentsDTO(post.getComments()));
    }

    public List<Post> findSuggestions(User user){
        return findAllUserPost(user).stream()
                .flatMap(post -> post.getComments().stream())
                .map(Comment::getOwner).filter(userID -> userID.getId() != user.getId())
                .flatMap(author -> findAllUserPost(author).stream().limit(5))
                .toList();
    }

    private List<ImageDTO> convertListOfPicturesToListOfPicturesDTO(List<Picture> pictures){
        return pictures.stream().map(imageService::convertSpecificImageToImageDTO).toList();
    }

    private List<CommentDTO> convertListOfCommentsToListOfCommentsDTO(List<Comment> comments){
        return comments.stream().map(commentService::convertCommentToCommentDTO).toList();
    }
}