package com.softuni.gms.app.garage_api.service;

import com.softuni.gms.app.model.InvoiceLog;
import com.softuni.gms.app.repository.InvoiceLogRepository;
import com.softuni.gms.app.web.dto.InvoiceRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "server.servlet.context-path=/api/v1"
)
public class InvoiceITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private InvoiceLogRepository invoiceLogRepository;

    @LocalServerPort
    private int port;

    @Test
    void testInvoicePDFGenerationAndMongoPersistence() {

        UUID repairId = UUID.randomUUID();

        InvoiceRequest request = InvoiceRequest.builder()
                .repairId(repairId)
                .createdAt(LocalDateTime.now().minusHours(1))
                .completedAt(LocalDateTime.now())
                .customerFirstName("Ivan")
                .customerLastName("Petrov")
                .customerPhone("0888123456")
                .mechanicFirstName("Test")
                .mechanicLastName("Mechanic")
                .carBrand("BMW")
                .carModel("E46")
                .partsTotal(BigDecimal.valueOf(50))
                .serviceFee(BigDecimal.valueOf(73.45))
                .totalPrice(BigDecimal.valueOf(123.45))
                .usedParts(Collections.emptyList())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InvoiceRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/pdf/invoices",
                HttpMethod.POST,
                entity,
                byte[].class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode(), "Status must be 200 OK");
        Assertions.assertNotNull(response.getBody(), "PDF must not be null");
        Assertions.assertTrue(response.getBody().length > 100, "PDF response seems too small");

        InvoiceLog saved = invoiceLogRepository.findByRepairId(repairId)
                .orElse(null);

        Assertions.assertNotNull(saved, "InvoiceLog must be stored in MongoDB");
        Assertions.assertEquals("BMW", saved.getCarBrand());
        Assertions.assertEquals("E46", saved.getCarModel());
        Assertions.assertEquals("Test", saved.getMechanicFirstName());
        Assertions.assertEquals("Mechanic", saved.getMechanicLastName());
        Assertions.assertEquals(BigDecimal.valueOf(123.45), saved.getTotalPrice());
    }
}
