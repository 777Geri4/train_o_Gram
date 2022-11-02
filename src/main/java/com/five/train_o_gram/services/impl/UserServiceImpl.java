package com.five.train_o_gram.services.impl;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.dto.UserRegistrationDTO;
import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.UserRepository;
import com.five.train_o_gram.services.ImageService;
import com.five.train_o_gram.util.exceptions.user.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements com.five.train_o_gram.services.UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ImageService imageService, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
        this.modelMapper = modelMapper;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.orElseThrow(UserNotFoundException::new);
    }

    public User findByLogin(String username){
        return userRepository.findByLogin(username);
    }

    @Transactional
    public User update(int id, UserRegistrationDTO userDTO){
        User user = convertUserDTOToUser(userDTO);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void registrationUser(UserRegistrationDTO userDTO){
        User user = convertUserDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public UserRegistrationDTO convertUserToUserRegistrationDTO(User user){
        return modelMapper.map(user, UserRegistrationDTO.class);
    }

    public UserDTO convertUserToUserDTO(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setAvatars(user.getAvatars().stream().map(imageService::convertSpecificImageToImageDTO).toList());
        return userDTO;
    }

    public User convertUserDTOToUser(UserRegistrationDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByLogin(authentication.getName());
        if (user == null) throw new UserNotFoundException();
        return user;
    }
}