package com.stephane.basicbanking.dto;

import com.stephane.basicbanking.domain.AccountType;
import com.stephane.basicbanking.domain.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountDTO {

    private Long id;

    private UUID accountId;

    private double balance;

    private User user;

    private AccountType accountType;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
