package com.five.train_o_gram.services;

import com.five.train_o_gram.dto.UserDTO;
import com.five.train_o_gram.dto.UserRegistrationDTO;
import com.five.train_o_gram.models.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findOne(int id);
    User findByLogin(String username);
    User update(int id, UserRegistrationDTO userDTO);
    void registrationUser(UserRegistrationDTO userDTO);
    void deleteUser(int id);
    User getCurrentUser();
    UserDTO convertUserToUserDTO(User user);
    User convertUserDTOToUser(UserRegistrationDTO userDTO);
    UserRegistrationDTO convertUserToUserRegistrationDTO(User user);
}