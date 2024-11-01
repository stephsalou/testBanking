package com.stephane.basicbanking.manager;

import com.stephane.basicbanking.customException.NullArgumentException;
import com.stephane.basicbanking.dto.AccountDTO;
import com.stephane.basicbanking.dto.TransactionDTO;
import com.stephane.basicbanking.dto.TransactionTypeDTO;
import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.dto.mapper.AccountMapper;
import com.stephane.basicbanking.dto.mapper.TransactionMapper;
import com.stephane.basicbanking.dto.mapper.TransactionTypeMapper;
import com.stephane.basicbanking.repository.TransactionRepository;
import com.stephane.basicbanking.service.JwtService;
import com.stephane.basicbanking.service.UserService;
import com.stephane.basicbanking.service.impl.AccountService;
import com.stephane.basicbanking.service.impl.TransactionService;
import com.stephane.basicbanking.service.impl.TransactionTypeService;
import com.stephane.basicbanking.utils.RequestContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionsManager {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final JwtService jwtService;
    private final UserService userService;
    private final TransactionTypeService transactionTypeService;
    private final TransactionTypeMapper transactionTypeMapper;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;


    public TransactionsManager(AccountService accountService, AccountMapper accountMapper, JwtService jwtService, UserService userService, TransactionTypeService transactionTypeService, TransactionTypeMapper transactionTypeMapper, TransactionService transactionService, TransactionMapper transactionMapper, TransactionRepository transactionRepository) {
        this.accountService = accountService;

        this.accountMapper = accountMapper;
        this.jwtService = jwtService;
        this.userService = userService;
        this.transactionTypeService = transactionTypeService;
        this.transactionTypeMapper = transactionTypeMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public TransactionDTO deposit(double amount, String accountId) throws NullArgumentException {

        String username = jwtService.extractUsername(RequestContext.getContext().getToken());
        if((amount <= 0)) {
            throw new NullArgumentException("Amount must be greater than 0");
        }
        if(accountId == null || accountId.isEmpty()) {
            throw new NullArgumentException("AccountId cannot be null or empty");
        }

        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.findByUsername(username)).orElse(Optional.empty());

        if(optionalUser.isEmpty()) {
            throw new NullArgumentException("User not found");
        }
        UserDTO user = optionalUser.get();

        Optional<AccountDTO> optionalAccount = Optional.ofNullable(accountMapper.toDto(accountService.findByAccountIdAndUserId(UUID.fromString(accountId),user.getId())));
        if(optionalAccount.isEmpty()) {
            throw new NullArgumentException("account not corresponding");
        }

        AccountDTO account = optionalAccount.get();
        Optional<TransactionTypeDTO> optionalTransactionTypeDTO = Optional.ofNullable(transactionTypeMapper.toDto(transactionTypeService.findTransactionTypeByName("DEPOSIT")));

        if(optionalTransactionTypeDTO.isEmpty()) {
            throw new NullArgumentException("Transaction type not found");
        }

        TransactionTypeDTO transactionTypeDTO = optionalTransactionTypeDTO.get();
        TransactionTypeDTO transactionType = optionalTransactionTypeDTO.get();
        account.setBalance(account.getBalance() + amount);
        account = accountMapper.toDto(accountService.save(accountMapper.toEntity(account)));

        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionTypeId(transactionType.getId());
        transaction.setTransactionTypeId(transactionType.getId());
        transaction.setSens("C");
        transaction.setActive(true);
        transaction.setMustBeShow(true);
        transaction.setUserId(user.getId());

        transaction = transactionMapper.toDto(transactionService.save(transactionMapper.toEntity(transaction)));
        return transaction;
    }

    @Transactional
    public TransactionDTO withdraw(double amount, String accountId) throws NullArgumentException {

        String username = jwtService.extractUsername(RequestContext.getContext().getToken());
        if((amount <= 0)) {
            throw new NullArgumentException("Amount must be greater than 0");
        }
        if(accountId == null || accountId.isEmpty()) {
            throw new NullArgumentException("AccountId cannot be null or empty");
        }

        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.findByUsername(username)).orElse(Optional.empty());

        if(optionalUser.isEmpty()) {
            throw new NullArgumentException("User not found");
        }
        UserDTO user = optionalUser.get();

        Optional<AccountDTO> optionalAccount = Optional.ofNullable(accountMapper.toDto(accountService.findByAccountIdAndUserId(UUID.fromString(accountId),user.getId())));
        if(optionalAccount.isEmpty()) {
            throw new NullArgumentException("account not corresponding");
        }

        AccountDTO account = optionalAccount.get();

        if((account.getBalance() - amount) < 0) {
            throw new NullArgumentException("Insufficient funds");
        }
        Optional<TransactionTypeDTO> optionalTransactionTypeDTO = Optional.ofNullable(transactionTypeMapper.toDto(transactionTypeService.findTransactionTypeByName("WITHDRAW")));

        if(optionalTransactionTypeDTO.isEmpty()) {
            throw new NullArgumentException("Transaction type not found");
        }

        TransactionTypeDTO transactionTypeDTO = optionalTransactionTypeDTO.get();
        TransactionTypeDTO transactionType = optionalTransactionTypeDTO.get();
        account.setBalance(account.getBalance() - amount);
        account = accountMapper.toDto(accountService.save(accountMapper.toEntity(account)));

        TransactionDTO transaction = new TransactionDTO();
        transaction.setAccountId(account.getId());
        transaction.setAmount(amount);
        transaction.setTransactionTypeId(transactionType.getId());
        transaction.setTransactionTypeId(transactionType.getId());
        transaction.setSens("D");
        transaction.setActive(true);
        transaction.setMustBeShow(true);
        transaction.setUserId(user.getId());

        transaction = transactionMapper.toDto(transactionService.save(transactionMapper.toEntity(transaction)));
        return transaction;
    }

}
