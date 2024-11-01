package com.stephane.basicbanking.Resource;

import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.dto.request.LoginDTO;
import com.stephane.basicbanking.service.AuthenticationService;
import com.stephane.basicbanking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthenticationResource {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    public AuthenticationResource(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authRequest(@RequestBody LoginDTO loginDTO) {
        log.info("AuthResource.authRequest start {}", loginDTO);
        Map<String, String> userSignInResponse = authenticationService.authRequest(loginDTO);
        log.info("userSignInResponse {}", userSignInResponse);
        return new ResponseEntity<>(userSignInResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO registeredUser = authenticationService.register(userDTO);
            return ResponseEntity.ok("User registered successfully with ID: " + registeredUser.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
