package com.stephane.basicbanking.Resource;


import com.stephane.basicbanking.domain.Transaction;
import com.stephane.basicbanking.dto.TransactionDTO;
import com.stephane.basicbanking.dto.request.DepositDTO;
import com.stephane.basicbanking.dto.request.HistoryDTO;
import com.stephane.basicbanking.dto.request.WithDrawDTO;
import com.stephane.basicbanking.manager.TransactionsManager;
import com.stephane.basicbanking.service.impl.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RestController
@RequestMapping("/api/transaction")
@Slf4j
public class TransactionResource {

    private final TransactionsManager transactionsManager;
    private TransactionService transactionService;
    private SpringTemplateEngine templateEngine;
    public TransactionResource(TransactionsManager transactionsManager, TransactionService transactionService) {
        this.transactionsManager = transactionsManager;
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public TransactionDTO deposit(DepositDTO depositDTO) {
        return transactionsManager.deposit(depositDTO.getAmount(), depositDTO.getAccountId());
    }

    @PostMapping("/withdraw")
    public TransactionDTO withdraw(WithDrawDTO withDrawDTO) {
        return transactionsManager.withdraw(withDrawDTO.getAmount(), withDrawDTO.getAccountId());
    }


    @GetMapping("/list")
    public ResponseEntity<Page<TransactionDTO>> getHistory(@RequestParam("page") int page, @RequestParam("size") int size) {
        page = page > 0 ? page : 1;
        size = size > 0 && size <= 100 ? size : 10;
        Pageable pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return new ResponseEntity<>(transactionService.getHistory(pageRequest), HttpStatus.OK);
    }
    @PostMapping("/list")
    public ResponseEntity<Page<TransactionDTO>> getHistory(@RequestBody HistoryDTO historyDTO) {
        int page = historyDTO.getPage() > 0 ? historyDTO.getPage() : 1;
        int size = historyDTO.getSize() > 0 && historyDTO.getSize() <= 100 ? historyDTO.getSize() : 10;
        Pageable pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());

        return new ResponseEntity<>(transactionService.getHistory(historyDTO,pageRequest), HttpStatus.OK);
    }
    @PostMapping("/generateReceipt")
    public String generateReceipt(@RequestBody HistoryDTO historyDTO) {

        int page = historyDTO.getPage() > 0 ? historyDTO.getPage() : 1;
        int size = historyDTO.getSize() > 0 && historyDTO.getSize() <= 100 ? historyDTO.getSize() : 10;
        Pageable pageRequest = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Transaction> transactionPage = transactionService.getReceipHistory(historyDTO,pageRequest);
        Context context = new Context();
        context.setVariable("transactions",transactionPage.stream().toList());
        return templateEngine.process("transaction", context);
    }
}
