package com.softuni.gms.web;

import com.softuni.gms.model.InvoiceLog;
import com.softuni.gms.service.InvoiceLogService;
import com.softuni.gms.service.PdfService;
import com.softuni.gms.web.dto.InvoiceRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/invoices")
    public ResponseEntity<byte[]> generateInvoice(@RequestBody @Valid InvoiceRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

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

    @GetMapping("/repair/{repairId}/latest")
    public ResponseEntity<byte[]> downloadLatestByRepairId(@PathVariable UUID repairId) {

        byte[] pdf = invoiceLogService.getLatestPdf(repairId);

        if (pdf == null || pdf.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + repairId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
