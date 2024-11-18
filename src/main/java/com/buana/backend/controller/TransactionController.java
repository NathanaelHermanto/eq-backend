package com.buana.backend.controller;

import com.buana.backend.model.Transaction;
import com.buana.backend.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Transaction>> getTransactionsByEmployeeId(@PathVariable Long employeeId) {
        return ResponseEntity.ok(transactionService.getTransactionsByEmployeeId(employeeId));
    }
}

