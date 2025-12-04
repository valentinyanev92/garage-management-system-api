package com.softuni.gms.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softuni.gms.model.InvoiceLog;
import com.softuni.gms.service.InvoiceLogService;
import com.softuni.gms.service.PdfService;
import com.softuni.gms.web.dto.InvoiceRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PdfController.class)
class PdfControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PdfService pdfService;

    @MockitoBean
    private InvoiceLogService invoiceLogService;

    private InvoiceRequest buildValidRequest() {
        InvoiceRequest req = new InvoiceRequest();
        req.setRepairId(UUID.randomUUID());
        req.setCustomerFirstName("Valentin");
        req.setCustomerLastName("Yanev");
        req.setCustomerPhone("0888123456");
        req.setCarBrand("BMW");
        req.setCarModel("E46");
        req.setServiceFee(BigDecimal.TEN);
        req.setPartsTotal(BigDecimal.TEN);
        req.setTotalPrice(BigDecimal.valueOf(20));
        req.setCreatedAt(LocalDateTime.now());
        return req;
    }

    @Test
    void testGenerateInvoice_success() throws Exception {
        InvoiceRequest req = buildValidRequest();
        req.setCompletedAt(LocalDateTime.now());
        req.setMechanicFirstName("Test");
        req.setMechanicLastName("Mechanic");

        byte[] pdf = "PDF-DATA".getBytes();

        Mockito.when(pdfService.generateInvoice(Mockito.any())).thenReturn(pdf);

        mockMvc.perform(post("/pdf/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=invoice.pdf"))
                .andExpect(content().bytes(pdf));
    }

    @Test
    void testGenerateInvoice_validationError_shouldReturn400() throws Exception {
        InvoiceRequest req = new InvoiceRequest(); // Invalid on purpose

        mockMvc.perform(post("/pdf/invoices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetInvoiceHistory_success() throws Exception {

        InvoiceLog log = new InvoiceLog();
        log.setId(String.valueOf(UUID.randomUUID()));

        Mockito.when(invoiceLogService.findAll()).thenReturn(List.of(log));

        mockMvc.perform(get("/pdf/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testGetByRepairId_success() throws Exception {

        InvoiceLog log = new InvoiceLog();
        log.setId(String.valueOf(UUID.randomUUID()));

        UUID repairId = UUID.randomUUID();

        Mockito.when(invoiceLogService.findByRepairId(repairId)).thenReturn(List.of(log));

        mockMvc.perform(get("/pdf/history/" + repairId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }

    @Test
    void testDownloadLatestPdf_success() throws Exception {

        byte[] pdf = "PDF-LATEST".getBytes();
        UUID repairId = UUID.randomUUID();

        Mockito.when(invoiceLogService.getLatestPdf(repairId)).thenReturn(pdf);

        mockMvc.perform(get("/pdf/repair/" + repairId + "/latest"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        "attachment; filename=invoice-" + repairId + ".pdf"))
                .andExpect(content().bytes(pdf));
    }

    @Test
    void testDownloadLatestPdf_notFound() throws Exception {

        UUID repairId = UUID.randomUUID();

        Mockito.when(invoiceLogService.getLatestPdf(repairId)).thenReturn(null);

        mockMvc.perform(get("/pdf/repair/" + repairId + "/latest"))
                .andExpect(status().isNotFound());
    }
}
