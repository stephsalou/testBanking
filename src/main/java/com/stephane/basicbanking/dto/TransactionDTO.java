package com.stephane.basicbanking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long id;
    private Long transactionTypeId;
    private String sens;
    private double amount;
    private Long userId;
    private Long accountId;
    private boolean mustBeShow;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}