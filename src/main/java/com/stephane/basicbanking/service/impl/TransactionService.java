package com.stephane.basicbanking.service.impl;

import com.stephane.basicbanking.customException.NullArgumentException;
import com.stephane.basicbanking.domain.Transaction;
import com.stephane.basicbanking.domain.TransactionType;
import com.stephane.basicbanking.dto.TransactionDTO;
import com.stephane.basicbanking.dto.UserDTO;
import com.stephane.basicbanking.dto.mapper.TransactionMapper;
import com.stephane.basicbanking.dto.request.HistoryDTO;
import com.stephane.basicbanking.repository.TransactionRepository;
import com.stephane.basicbanking.service.JwtService;
import com.stephane.basicbanking.service.UserService;
import com.stephane.basicbanking.utils.RequestContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService extends BaseServiceImpl<Transaction, Long> {
    
    private final TransactionRepository repository;
    private final TransactionMapper mapper;
    private final JwtService jwtService;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper mapper, JwtService jwtService, UserService userService) {
        this.repository = transactionRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public Page<TransactionDTO> getHistory(Pageable pageable) throws NullArgumentException {
        String username = jwtService.extractUsername(RequestContext.getContext().getToken());

        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.findByUsername(username)).orElse(Optional.empty());

        if(optionalUser.isEmpty()) {
            throw new NullArgumentException("User not found");
        }

        UserDTO user = optionalUser.get();
        return repository.findByUserId(user.getId(), pageable).map(mapper::toDto);

    }
    public Page<TransactionDTO> getHistory(HistoryDTO historyDTO, Pageable pageable) throws NullArgumentException {
        String username = jwtService.extractUsername(RequestContext.getContext().getToken());

        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.findByUsername(username)).orElse(Optional.empty());

        if(optionalUser.isEmpty()) {
            throw new NullArgumentException("User not found");
        }

        UserDTO user = optionalUser.get();
        return repository.getHistory(user.getId(),historyDTO.getTransactionTypeId(),historyDTO.getSens(),historyDTO.getAccountId(),historyDTO.getFromDate(),historyDTO.getToDate() , pageable).map(mapper::toDto);

    }
    public Page<Transaction> getReceipHistory(HistoryDTO historyDTO, Pageable pageable) throws NullArgumentException {
        String username = jwtService.extractUsername(RequestContext.getContext().getToken());

        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.findByUsername(username)).orElse(Optional.empty());

        if(optionalUser.isEmpty()) {
            throw new NullArgumentException("User not found");
        }

        UserDTO user = optionalUser.get();
        return repository.getHistory(user.getId(),historyDTO.getTransactionTypeId(),historyDTO.getSens(),historyDTO.getAccountId(),historyDTO.getFromDate(),historyDTO.getToDate() , pageable);

    }
}
