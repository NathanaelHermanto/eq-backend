package com.buana.backend.controller;

import com.buana.backend.model.LogTransaction;
import com.buana.backend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/log-transactions")
public class LogTransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<LogTransaction>> getTransactions() {
        return ResponseEntity.ok(transactionService.getLogTransactions());
    }

}

