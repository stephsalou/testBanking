package com.stephane.basicbanking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {


    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is required")
    private String username;


    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
}
