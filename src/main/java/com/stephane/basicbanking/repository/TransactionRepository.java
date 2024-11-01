package com.stephane.basicbanking.repository;

import com.stephane.basicbanking.domain.Transaction;
import com.stephane.basicbanking.domain.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    Page<Transaction> findByUserId(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM transaction WHERE user_id = :userId and transaction_type_id = :transationTypeId and sens = :sens and account_id = :accountId and created_at >= :fromDate and created_at <= :toDate ", nativeQuery = true)
    Page<Transaction> getHistory(Long userId,Long transationTypeId , String sens,Long accountId ,String fromDate , String toDate , Pageable pageable);
}
