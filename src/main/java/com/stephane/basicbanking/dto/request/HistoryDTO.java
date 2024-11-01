package com.stephane.basicbanking.dto.request;


import lombok.Data;

@Data
public class HistoryDTO {
    private Long transactionTypeId;
    private String sens;
    private Long accountId;
    private String fromDate;
    private String toDate;
    private int page;
    private int size;
}
