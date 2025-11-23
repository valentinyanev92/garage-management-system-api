package com.softuni.gms.app.garage_api.service;

import com.softuni.gms.app.model.InvoiceLog;
import com.softuni.gms.app.repository.InvoiceLogRepository;
import com.softuni.gms.app.service.InvoiceLogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class InvoiceLogServiceUTest {

    @Mock
    private InvoiceLogRepository invoiceLogRepository;

    @InjectMocks
    private InvoiceLogService invoiceLogService;


    @Test
    void testFindAll_returnsList() {

        List<InvoiceLog> logs = List.of(new InvoiceLog(), new InvoiceLog());
        Mockito.when(invoiceLogRepository.findAll()).thenReturn(logs);

        List<InvoiceLog> result = invoiceLogService.findAll();

        Assertions.assertEquals(2, result.size());
    }


    @Test
    void testFindByRepairId_returnsCorrectLogs() {

        UUID id = UUID.randomUUID();
        List<InvoiceLog> logs = List.of(new InvoiceLog());

        Mockito.when(invoiceLogRepository.findAllByRepairId(id)).thenReturn(logs);

        List<InvoiceLog> result = invoiceLogService.findByRepairId(id);

        Assertions.assertEquals(1, result.size());
    }


    @Test
    void testFindLatestByRepairId_returnsOptionalLog() {

        UUID id = UUID.randomUUID();
        InvoiceLog log = new InvoiceLog();
        log.setGeneratedAt(LocalDateTime.now());

        Mockito.when(invoiceLogRepository.findFirstByRepairIdOrderByGeneratedAtDesc(id))
                .thenReturn(Optional.of(log));

        Optional<InvoiceLog> result = invoiceLogService.findLatestByRepairId(id);

        Assertions.assertTrue(result.isPresent());
    }


    @Test
    void testGetLatestPdf_returnsDocumentBytes() {

        UUID id = UUID.randomUUID();

        InvoiceLog log = new InvoiceLog();
        log.setDocument(new byte[]{1, 2, 3});

        Mockito.when(invoiceLogRepository.findFirstByRepairIdOrderByGeneratedAtDesc(id))
                .thenReturn(Optional.of(log));

        byte[] data = invoiceLogService.getLatestPdf(id);

        Assertions.assertNotNull(data);
        Assertions.assertArrayEquals(new byte[]{1, 2, 3}, data);
    }


    @Test
    void testGetLatestPdf_emptyDocument_returnsNull() {

        UUID id = UUID.randomUUID();

        InvoiceLog log = new InvoiceLog();
        log.setDocument(new byte[]{});

        Mockito.when(invoiceLogRepository.findFirstByRepairIdOrderByGeneratedAtDesc(id))
                .thenReturn(Optional.of(log));

        byte[] data = invoiceLogService.getLatestPdf(id);

        Assertions.assertNull(data);
    }


    @Test
    void testGetLatestPdf_noLogFound_returnsNull() {

        UUID id = UUID.randomUUID();

        Mockito.when(invoiceLogRepository.findFirstByRepairIdOrderByGeneratedAtDesc(id))
                .thenReturn(Optional.empty());

        byte[] data = invoiceLogService.getLatestPdf(id);

        Assertions.assertNull(data);
    }
}
