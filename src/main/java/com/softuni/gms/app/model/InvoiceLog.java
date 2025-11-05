package com.softuni.gms.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document(collection = "invoice_logs")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceLog {

    @Id
    private String id;
    private UUID repairId;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    private LocalDateTime generatedAt;
    private String customerFirstName;
    private String customerLastName;
    private String customerPhone;
    private String mechanicFirstName;
    private String mechanicLastName;
    private String carBrand;
    private String carModel;
    private BigDecimal partsTotal;
    private BigDecimal serviceFee;
    private BigDecimal totalPrice;

    private List<UsedPartInfo> usedParts;
}
