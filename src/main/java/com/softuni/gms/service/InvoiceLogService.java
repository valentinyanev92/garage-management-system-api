package com.softuni.gms.service;

import com.softuni.gms.model.InvoiceLog;
import com.softuni.gms.repository.InvoiceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InvoiceLogService {

    private final InvoiceLogRepository invoiceLogRepository;

    @Autowired
    public InvoiceLogService(InvoiceLogRepository invoiceLogRepository) {

        this.invoiceLogRepository = invoiceLogRepository;
    }

    public List<InvoiceLog> findAll() {

        return invoiceLogRepository.findAll();
    }

    public List<InvoiceLog> findByRepairId(UUID repairId) {

        return invoiceLogRepository.findAllByRepairId(repairId);
    }
}
