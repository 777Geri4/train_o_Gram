package com.five.train_o_gram.services;

import com.five.train_o_gram.dto.ImageDTO;
import com.five.train_o_gram.models.Avatar;
import com.five.train_o_gram.models.Picture;
import com.five.train_o_gram.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class ImageService {
    private static final char SEPARATOR = File.separatorChar;
    private static final String ROOT_AVATAR_FOLDER = "C:" + SEPARATOR + "Avatars" + SEPARATOR;
    private static final String DEFAULT_AVATAR_FOLDER = "default" + SEPARATOR + "default.jpg";
    private static final String ROOT_POST_PICTURE_FOLDER =  "C:" + SEPARATOR + "PostPictures" + SEPARATOR;

    private final ModelMapper modelMapper;

    public ImageService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Path saveAvatarToDisk(User user, MultipartFile image) throws IOException {
        File theDir = new File(ROOT_AVATAR_FOLDER + user.getLogin());
        return saveImageToDisk(theDir, image);
    }

    public Path savePostPictureToDisk(User user, MultipartFile image) throws IOException {
        File theDir = new File(ROOT_POST_PICTURE_FOLDER + user.getLogin());
        return saveImageToDisk(theDir, image);
    }

    public byte[] loadAvatarFromDisk(Optional<Avatar> avatar) throws IOException {
        byte[] bytes;
        if (avatar.isEmpty()) {
            bytes = Files.readAllBytes(Paths.get(ROOT_AVATAR_FOLDER + DEFAULT_AVATAR_FOLDER));
        } else {
            bytes = Files.readAllBytes(Paths.get(avatar.get().getPath()));
        }
        return bytes;
    }

    private Path saveImageToDisk(File theDir, MultipartFile image) throws IOException {
        if (!theDir.exists()) theDir.mkdirs();
        byte [] bytes = image.getBytes();
        Path path = Paths.get(theDir.getPath() + SEPARATOR + image.getOriginalFilename());
        Files.write(path, bytes);
        return path;
    }

    public ImageDTO convertSpecificImageToImageDTO(Picture picture){
        return modelMapper.map(picture, ImageDTO.class);
    }

    public ImageDTO convertSpecificImageToImageDTO(Avatar avatar){
        return modelMapper.map(avatar, ImageDTO.class);
    }
}
