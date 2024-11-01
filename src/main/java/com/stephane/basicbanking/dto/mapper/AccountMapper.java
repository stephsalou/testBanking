package com.stephane.basicbanking.dto.mapper;

import com.stephane.basicbanking.domain.Account;
import com.stephane.basicbanking.dto.AccountDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper extends EntityMapper<AccountDTO, Account> {
    default List<String> map(String roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(roles.split(","));
    }

    default String map(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return String.join(",", roles);
    }
}
