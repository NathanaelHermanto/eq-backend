package com.buana.backend.repository;

import com.buana.backend.model.LogTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogTransactionRepository extends JpaRepository<LogTransaction, Long> {
}
