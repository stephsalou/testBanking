package com.stephane.basicbanking.dto.request;

import lombok.Data;

@Data
public class DepositDTO {
    private String AccountId;
    private double amount;
}
