package com.stephane.basicbanking.repository;

import com.stephane.basicbanking.domain.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findByName(String transactionTypeName);

}
