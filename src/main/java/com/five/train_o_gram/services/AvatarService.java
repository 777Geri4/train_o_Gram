package com.five.train_o_gram.services;

import com.five.train_o_gram.models.Avatar;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final ImageService imageService;

    @Autowired
    public AvatarService(AvatarRepository avatarRepository, ImageService imageService) {
        this.avatarRepository = avatarRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void createAvatar(User user, MultipartFile image) throws IOException {
        Path path = imageService.saveAvatarToDisk(user, image);
        avatarRepository.save(new Avatar(path.toString(), new Date(), user));
    }

    public List<Avatar> getUserAvatars(User user) {
        return avatarRepository.findAllIdByOwnerId(user.getId());
    }

    public byte[] showAvatar(int id) throws IOException {
        Optional <Avatar> avatar = avatarRepository.findById(id);
        return imageService.loadAvatarFromDisk(avatar);
    }

    @Transactional
    public void deleteAvatar(int id){
        avatarRepository.deleteById(id);
    }
}