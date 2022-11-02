package com.five.train_o_gram.services;

import com.five.train_o_gram.dto.PostDTO;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<Post> findAllUserPost(User user);
    Post findPostByID(int id);
    void createPost(User user, MultipartFile image, String message) throws IOException;
    void deletePost(int id);
    Post updatePost(Post post);
    PostDTO convertPostToPostDTO(Post post);


}
