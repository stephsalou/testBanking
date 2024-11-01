package com.stephane.basicbanking.service;


import com.stephane.basicbanking.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface UserService {


    Optional<UserDTO> updateUser(UserDTO userDTO);

    Page<UserDTO> getAllUser(Pageable pageable);
    Optional<UserDTO> findByUsername(String username);

}
