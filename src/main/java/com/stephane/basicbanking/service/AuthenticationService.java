package com.stephane.basicbanking.service;

import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.dto.request.LoginDTO;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface AuthenticationService {
        Map<String, String> authRequest(LoginDTO loginDTO);
        UserDTO register(UserDTO userDTO);
}