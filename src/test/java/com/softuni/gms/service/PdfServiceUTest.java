package com.softuni.gms.service;

import com.softuni.gms.model.InvoiceLog;
import com.softuni.gms.repository.InvoiceLogRepository;
import com.softuni.gms.web.dto.InvoiceRequest;
import com.softuni.gms.web.dto.UsedPartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PdfServiceUTest {

    @Mock
    private InvoiceLogRepository invoiceLogRepository;

    @InjectMocks
    private PdfService pdfService;

    @Test
    void testGenerateInvoice_savesInvoiceLogAndReturnsPdfBytes() {

        UUID repairId = UUID.randomUUID();

        InvoiceRequest request = InvoiceRequest.builder()
                .repairId(repairId)
                .customerFirstName("Valentin")
                .customerLastName("Yanev")
                .customerPhone("0888123456")
                .carBrand("BMW")
                .carModel("E46")
                .mechanicFirstName("Gosho")
                .mechanicLastName("Goshov")
                .createdAt(LocalDateTime.now().minusDays(1))
                .completedAt(LocalDateTime.now())
                .partsTotal(BigDecimal.TEN)
                .serviceFee(BigDecimal.TEN)
                .totalPrice(BigDecimal.valueOf(20))
                .usedParts(List.of(
                        UsedPartRequest.builder()
                                .partName("Oil Filter")
                                .quantity(1)
                                .unitPrice(BigDecimal.TEN)
                                .totalPrice(BigDecimal.TEN)
                                .build()
                ))
                .build();

        byte[] resultPdf = pdfService.generateInvoice(request);

        Assertions.assertNotNull(resultPdf);
        Assertions.assertTrue(resultPdf.length > 0);

        ArgumentCaptor<InvoiceLog> logCaptor = ArgumentCaptor.forClass(InvoiceLog.class);
        verify(invoiceLogRepository).save(logCaptor.capture());

        InvoiceLog saved = logCaptor.getValue();

        Assertions.assertEquals(repairId, saved.getRepairId());
        Assertions.assertNotNull(saved.getDocument());
        Assertions.assertTrue(saved.getDocument().length > 0);
    }
}
