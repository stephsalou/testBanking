package com.stephane.basicbanking.service.impl;

import com.stephane.basicbanking.domain.Account;
import com.stephane.basicbanking.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService extends BaseServiceImpl<Account, Long> {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        this.repository = accountRepository;
    }

    public Account findByAccountId(UUID accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    public Account findByAccountIdAndUserId(UUID accountId, Long userId) {
        return accountRepository.findByAccountIdAndUser_id(accountId, userId);
    }
}
