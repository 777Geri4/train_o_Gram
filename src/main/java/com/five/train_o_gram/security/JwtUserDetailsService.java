package com.five.train_o_gram.security;

import com.five.train_o_gram.models.User;
import com.five.train_o_gram.repositories.UserRepository;
import com.five.train_o_gram.security.jwt.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByLogin(username);
        if (user == null) throw new UsernameNotFoundException("User with login " + username + "isn't exist");
        return new JwtUser(user);
    }
}