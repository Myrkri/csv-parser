package com.example.csv.repository.entity;

import com.example.csv.model.enums.ProcessingStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "upload_status")
@Data
public class StatusStorageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "process_id", nullable = false)
    private String processId;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessingStatus status;
    @Column(name = "processed_records", nullable = false)
    private long processedRecords;

}
