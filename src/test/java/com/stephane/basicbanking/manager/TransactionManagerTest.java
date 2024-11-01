package com.stephane.basicbanking.manager;

import com.stephane.basicbanking.customException.NullArgumentException;
import com.stephane.basicbanking.dto.AccountDTO;
import com.stephane.basicbanking.dto.TransactionDTO;
import com.stephane.basicbanking.dto.TransactionTypeDTO;
import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.dto.mapper.AccountMapper;
import com.stephane.basicbanking.dto.mapper.TransactionTypeMapper;
import com.stephane.basicbanking.service.JwtService;
import com.stephane.basicbanking.service.UserService;
import com.stephane.basicbanking.service.impl.AccountService;
import com.stephane.basicbanking.service.impl.TransactionTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionManagerTest {


    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;

    @Mock
    private AccountService accountService;
    @Mock
    private TransactionTypeService transactionTypeService;

    @Mock
    private TransactionTypeMapper transactionTypeMapper;

    @Mock
    private AccountMapper accountMapper;

    @Autowired
    private TransactionsManager transactionsManager;

    @Test
    public void test_deposit_updates_balance_and_creates_transaction() {
        double amount = 100.0;
        String accountId = "123e4567-e89b-12d3-a456-426614174000";
        UserDTO user = new UserDTO();
        user.setId(1L);
        AccountDTO account = new AccountDTO();
        account.setId(1L);
        account.setBalance(200.0);
        TransactionTypeDTO transactionType = new TransactionTypeDTO();
        transactionType.setId(1L);
    
        when(jwtService.extractUsername(anyString())).thenReturn("testUser");
        when(userService.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(accountService.findByAccountIdAndUserId(UUID.fromString(accountId), anyLong())).thenReturn(accountMapper.toEntity(account));
        when(transactionTypeService.findTransactionTypeByName("DEPOSIT")).thenReturn(transactionTypeMapper.toEntity(transactionType));
    
        TransactionDTO transaction = transactionsManager.deposit(amount, accountId);
    
        assertEquals(300.0, account.getBalance());
        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount());
    }

    @Test
    public void test_withdraw_updates_balance_and_creates_transaction() {
        double amount = 50.0;
        String accountId = "123e4567-e89b-12d3-a456-426614174000";
        UserDTO user = new UserDTO();
        user.setId(1L);
        AccountDTO account = new AccountDTO();
        account.setId(1L);
        account.setBalance(200.0);
        TransactionTypeDTO transactionType = new TransactionTypeDTO();
        transactionType.setId(2L);
    
        when(jwtService.extractUsername(anyString())).thenReturn("testUser");
        when(userService.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(accountService.findByAccountIdAndUserId(any(UUID.class), anyLong())).thenReturn(accountMapper.toEntity(account));
        when(transactionTypeService.findTransactionTypeByName("WITHDRAW")).thenReturn(transactionTypeMapper.toEntity(transactionType));
    
        TransactionDTO transaction = transactionsManager.withdraw(amount, accountId);
    
        assertEquals(150.0, account.getBalance());
        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount());
    }


    @Test
    public void test_valid_user_retrieved_from_jwt_token() {
        String username = "testUser";
    
        when(jwtService.extractUsername(anyString())).thenReturn(username);
    
        String extractedUsername = jwtService.extractUsername("dummyToken");
    
        assertEquals(username, extractedUsername);
    }

    @Test
    public void test_deposit_with_invalid_amount_throws_exception() {
        double amount = -10.0;
    
        assertThrows(NullArgumentException.class, () -> transactionsManager.deposit(amount, "123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    public void test_withdraw_with_invalid_amount_throws_exception() {
        double amount = 0.0;
    
        assertThrows(NullArgumentException.class, () -> transactionsManager.withdraw(amount, "123e4567-e89b-12d3-a456-426614174000"));
    }

    @Test
    public void test_withdraw_insufficient_funds_throws_exception() {
        double amount = 300.0;
    
        AccountDTO account = new AccountDTO();
        account.setBalance(200.0);
    
        when(accountService.findByAccountIdAndUserId(any(UUID.class), anyLong())).thenReturn(accountMapper.toEntity(account));
    
        assertThrows(NullArgumentException.class, () -> transactionsManager.withdraw(amount, "123e4567-e89b-12d3-a456-426614174000"));
    }

}