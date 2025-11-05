package com.softuni.gms.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {

    @NotNull
    private UUID repairId;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime completedAt;

    @NotBlank
    private String customerFirstName;

    @NotBlank
    private String customerLastName;

    @NotBlank
    private String customerPhone;

    @NotBlank
    private String mechanicFirstName;

    @NotBlank
    private String mechanicLastName;

    @NotBlank
    private String carBrand;

    @NotBlank
    private String carModel;

    @NotNull
    private BigDecimal partsTotal;

    @NotNull
    private BigDecimal serviceFee;

    @NotNull
    private BigDecimal totalPrice;
    private List<UsedPartRequest> usedParts;
}
