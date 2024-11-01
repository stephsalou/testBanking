package com.stephane.basicbanking.service.impl;

import com.stephane.basicbanking.domain.TransactionType;
import com.stephane.basicbanking.repository.TransactionTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionTypeService extends BaseServiceImpl<TransactionType, Long> {

    private final TransactionTypeRepository transactionTypeRepository;

    public TransactionTypeService(TransactionTypeRepository transactionTypeRepository) {
        this.transactionTypeRepository = transactionTypeRepository;
        this.repository = transactionTypeRepository;
    }
    public TransactionType findTransactionTypeByName(String name) {
        return transactionTypeRepository.findByName(name);
    }

}
