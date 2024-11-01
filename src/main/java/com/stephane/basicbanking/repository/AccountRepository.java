package com.stephane.basicbanking.repository;

import com.stephane.basicbanking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(UUID accountId);

    Account findByAccountIdAndUser_id(UUID accountId, Long userId);
}
