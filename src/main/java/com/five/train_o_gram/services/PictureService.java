package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Picture;
import com.five.train_o_gram.models.Post;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.PictureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class PictureService {
    private final PictureRepository pictureRepository;
    private final ImageService imageService;

    @Autowired
    public PictureService(PictureRepository pictureRepository, ImageService imageService) {
        this.pictureRepository = pictureRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void createPicture(User user, MultipartFile image, Post post) throws IOException {
        Path path = imageService.savePostPictureToDisk(user, image);
        pictureRepository.save(new Picture(path.toString(), new Date(), post));
    }
}