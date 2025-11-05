package com.softuni.gms.app.web.mapper;

import com.softuni.gms.app.model.InvoiceLog;
import com.softuni.gms.app.model.UsedPartInfo;
import com.softuni.gms.app.web.dto.InvoiceRequest;
import com.softuni.gms.app.web.dto.RepairCompletionRequest;
import com.softuni.gms.app.web.dto.UsedPartRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DtoMapper {

    public static InvoiceLog mapInvoiceRequestToInvoiceLog(InvoiceRequest request) {

        return InvoiceLog.builder()
                .id(UUID.randomUUID().toString())
                .repairId(request.getRepairId())
                .createdAt(request.getCreatedAt())
                .completedAt(request.getCompletedAt())
                .generatedAt(LocalDateTime.now())
                .customerFirstName(request.getCustomerFirstName())
                .customerLastName(request.getCustomerLastName())
                .customerPhone(request.getCustomerPhone())
                .mechanicFirstName(request.getMechanicFirstName())
                .mechanicLastName(request.getMechanicLastName())
                .carBrand(request.getCarBrand())
                .carModel(request.getCarModel())
                .partsTotal(request.getPartsTotal())
                .serviceFee(request.getServiceFee())
                .totalPrice(request.getTotalPrice())
                .usedParts(mapParts(request.getUsedParts()))
                .build();

    }

    private static List<UsedPartInfo> mapParts(List<UsedPartRequest> usedPartRequestList) {

        if (usedPartRequestList == null) return java.util.List.of();

        return usedPartRequestList.stream()
                .map(p -> UsedPartInfo.builder()
                        .partName(p.getPartName())
                        .unitPrice(p.getUnitPrice())
                        .quantity(p.getQuantity())
                        .totalPrice(p.getTotalPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public static String repairCompletionRequestToString(RepairCompletionRequest request){

        return String.format("Repair order for %s %s is completed by %s %s!",
                request.getCarBrand(),
                request.getCarModel(),
                request.getMechanicFistName(),
                request.getMechanicLastName());

    }
}
