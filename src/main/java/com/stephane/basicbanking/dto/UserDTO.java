package com.stephane.basicbanking.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stephane.basicbanking.domain.Account;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String password;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    private List<AccountDTO> accounts;
}
