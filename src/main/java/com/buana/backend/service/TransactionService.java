package com.buana.backend.service;

import com.buana.backend.model.Employee;
import com.buana.backend.model.Fee;
import com.buana.backend.model.LogTransaction;
import com.buana.backend.model.Transaction;
import com.buana.backend.repository.EmployeeRepository;
import com.buana.backend.repository.FeeRepository;
import com.buana.backend.repository.LogTransactionRepository;
import com.buana.backend.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

   private final TransactionRepository transactionRepository;

   private final EmployeeRepository employeeRepository;

   private final FeeRepository feeRepository;

   private final LogTransactionRepository logTransactionRepository;

   private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

   public List<Transaction> getTransactionsByEmployeeId(Long employeeId) {
       return transactionRepository.findByEmployee_EmployeeId(employeeId);
   }

   public Transaction createTransaction(Employee employee, Double amount, LocalDate transactionDate) {
       Transaction transaction = Transaction.builder()
               .employee(employee)
               .amount(amount)
               .date(transactionDate)
               .build();

       Transaction saved = transactionRepository.save(transaction);

         logger.info("Transaction created with id {}", saved.getTransactionId());

       return saved;
   }

   /**
    * Process the uploaded CSV file
    * input csv file format = {employee_id};{Amount};{tgl_transaksi}
    * flow when processing the file:
    * 1. read the file
    * 2. for each line, split the line by ";"
    * 3. create a new Transaction object with the data from the line
    * 4. create a new fee
    * 5. create log transaction csv_filename, Total_record, Total_record_faild,
    * Total_record_succes, Faild_id_notes dan Upload_date.
    */
    @Transactional
    public String processCsvFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        int totalRecords = 0;
        int totalSuccess = 0;
        int totalFailures = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                totalRecords++;
                try {
                    // Parse CSV line
                    String[] parts = line.split(";");
                    Long employeeId = Long.parseLong(parts[0].trim());
                    Double amount = Double.parseDouble(parts[1].trim());
                    LocalDate transactionDate = LocalDate.parse(parts[2].trim());

                    // Validate employee ID
                    Employee employee = employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + employeeId));

                    createTransaction(employee, amount, transactionDate);

                    // Calculate Fee
//                    calculateAndSaveFee(employee, amount, transactionDate);

                    totalSuccess++;

                } catch (Exception e) {
                    totalFailures++;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing CSV file: " + e.getMessage());
        }

        // Save Process Log
        saveProcessLog(fileName, totalRecords, totalSuccess, totalFailures);

        return "Processed CSV File: " + fileName + " | Total Records: " + totalRecords +
                " | Success: " + totalSuccess + " | Failures: " + totalFailures;

    }

    private void saveProcessLog(String fileName, int totalRecords, int totalSuccess, int totalFailures) {
        LogTransaction saved =  logTransactionRepository.save(LogTransaction.builder()
                .filename(fileName)
                .totalRecord(totalRecords)
                .successRecord(totalSuccess)
                .failedRecord(totalFailures)
                .failedNotes("Failed to get employee ID or parse CSV line")
                .date(LocalDate.now())
                .build());

        logger.info("Log Transaction created with id {}", saved.getId());
    }

    private void calculateAndSaveFee(Employee employee, Double amount, LocalDate date) {
        List<Employee> subordinates = getAllSubordinates(employee.getEmployeeId());
        int hierarchyLevel = subordinates.size();

        if (hierarchyLevel > 0) {
            double fee = amount / hierarchyLevel;
            for (Employee subordinate : subordinates) {
                Fee feeRecord = Fee.builder()
                        .employee(subordinate)
                        .amount_fee(fee)
                        .date_fee(date)
                        .build();

                feeRepository.save(feeRecord);
            }
        }
    }

    private List<Employee> getAllSubordinates(Long managerId) {
        List<Employee> directSubordinates = employeeRepository.findEmployeesByEmployeeManagerId(managerId);
        List<Employee> allSubordinates = new ArrayList<>(directSubordinates);

        for (Employee subordinate : directSubordinates) {
            allSubordinates.addAll(getAllSubordinates(subordinate.getEmployeeId()));
        }

        return allSubordinates;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<LogTransaction> getLogTransactions() {
        return logTransactionRepository.findAll();
    }
}
