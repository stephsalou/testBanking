package com.stephane.basicbanking.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TransactionTypeDTO {

    private Long id;
    private String name;
    private String description;
    private boolean active;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}