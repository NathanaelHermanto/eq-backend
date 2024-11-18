package com.buana.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@Table(name = "log_transaction")
public class LogTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private Integer totalRecord;
    private Integer successRecord;
    private Integer failedRecord;
    private String failedNotes;
    private LocalDate date;
}
