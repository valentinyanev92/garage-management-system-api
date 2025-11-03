package com.softuni.gms.web;

import com.softuni.gms.model.InvoiceLog;
import com.softuni.gms.service.InvoiceLogService;
import com.softuni.gms.service.PdfService;
import com.softuni.gms.web.dto.InvoiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfService pdfService;
    private final InvoiceLogService invoiceLogService;

    @Autowired
    public PdfController(PdfService pdfService, InvoiceLogService invoiceLogService) {
        this.pdfService = pdfService;
        this.invoiceLogService = invoiceLogService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody InvoiceRequest request) {

        byte[] pdfBytes = pdfService.generateInvoice(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @GetMapping("/history")
    public ResponseEntity<List<InvoiceLog>> getInvoiceHistory() {

        List<InvoiceLog> invoices = invoiceLogService.findAll();
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/history/{repairId}")
    public ResponseEntity<List<InvoiceLog>> getByRepairId(@PathVariable UUID repairId) {

        List<InvoiceLog> invoices = invoiceLogService.findByRepairId(repairId);
        return ResponseEntity.ok(invoices);
    }
}
