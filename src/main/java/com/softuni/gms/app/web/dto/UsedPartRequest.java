package com.softuni.gms.app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedPartRequest {

    @NotBlank
    private String partName;

    @NotNull
    private BigDecimal unitPrice;

    @NotNull
    private int quantity;

    @NotNull
    private BigDecimal totalPrice;
}
