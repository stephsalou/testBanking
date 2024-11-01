package com.stephane.basicbanking.dto.mapper;

import com.stephane.basicbanking.domain.Transaction;
import com.stephane.basicbanking.dto.TransactionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
}
