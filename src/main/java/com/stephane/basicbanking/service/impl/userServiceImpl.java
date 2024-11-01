package com.stephane.basicbanking.service.impl;

import com.stephane.basicbanking.domain.User;
import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.repository.UserRepository;
import com.stephane.basicbanking.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userServiceImpl implements UserService , UserDetailsService {


    private final UserRepository userRepository;

    public userServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.empty();
    }

    @Override
    public Page<UserDTO> getAllUser(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return Optional.empty();
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser =  Optional.ofNullable(userRepository.findOneByUsername(username))
                .orElseThrow(()-> new RuntimeException(String.format( "user = %s not exist", username)));
        return optionalUser.get();
    }
}
