package com.stephane.basicbanking.dto.mapper;

import com.stephane.basicbanking.domain.Account;
import com.stephane.basicbanking.domain.TransactionType;
import com.stephane.basicbanking.dto.AccountDTO;
import com.stephane.basicbanking.dto.TransactionTypeDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper extends EntityMapper<TransactionTypeDTO, TransactionType> {
}
