package com.stephane.basicbanking.service.impl;

import com.stephane.basicbanking.domain.User;
import com.stephane.basicbanking.dto.UserDTO;
//import com.stephane.basicbanking.dto.mapper.UserMapper;
import com.stephane.basicbanking.dto.request.LoginDTO;
import com.stephane.basicbanking.repository.UserRepository;
import com.stephane.basicbanking.service.AuthenticationService;
import com.stephane.basicbanking.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService   {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
//    private final UserMapper userMapper;

    @Override
    public Map<String, String> authRequest(LoginDTO loginDTO) {
        final var authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        final var userDetails =  (UserDetails) authenticate.getPrincipal();
        return   getToken(userDetails);
    }

    public Map<String, String> getToken( UserDetails userDetails) {
        final var roles = userDetails.getAuthorities();
        final var username = userDetails.getUsername();
        final var token = jwtService.generateToken(Map.of("role", roles), username);
        return Map.of("token", token);
    }

    @Override
    public UserDTO register(UserDTO userDTO) /* throws UserAlreadyExistsException */ {
        if (userRepository.existsByUsername(userDTO.getUsername()) || userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Username or email already exists.");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
//        return userMapper.toDto(userRepository.save(user));
        return new UserDTO();
    }

}
