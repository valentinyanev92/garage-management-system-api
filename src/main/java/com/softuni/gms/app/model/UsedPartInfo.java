package com.softuni.gms.app.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsedPartInfo {

    private String partName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
