package com.stephane.basicbanking.dto.request;

import lombok.Data;

@Data
public class WithDrawDTO {
    private String AccountId;
    private double amount;
}
