package com.softuni.gms.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "repair_logs")
public class RepairLog {

    @Id
    private String id;

    private String repairId;
    private String status;
    private String message;
    private LocalDateTime receivedAt;
}
