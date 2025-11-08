package com.softuni.gms.app.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.softuni.gms.app.model.InvoiceLog;
import com.softuni.gms.app.repository.InvoiceLogRepository;
import com.softuni.gms.app.web.dto.InvoiceRequest;
import com.softuni.gms.app.web.dto.UsedPartRequest;
import com.softuni.gms.app.web.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PdfService {

        private final InvoiceLogRepository invoiceLogRepository;


        //TODO - REMOVE TRY-CATCH??
        public byte[] generateInvoice(InvoiceRequest request) {

            Document document = new Document(PageSize.A4, 40, 40, 60, 40);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                PdfWriter.getInstance(document, outputStream);
                document.open();

                try {
                    ClassPathResource logoResource = new ClassPathResource("static/images/full-logo.png");
                    Image logo = Image.getInstance(logoResource.getInputStream().readAllBytes());
                    logo.scaleToFit(200, 100);
                    logo.setAlignment(Element.ALIGN_CENTER);
                    document.add(logo);
                    document.add(Chunk.NEWLINE);
                } catch (Exception e) {
                    System.err.println("Could not load logo: " + e.getMessage());
                }

                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
                Paragraph title = new Paragraph("Repair Invoice", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
                document.add(new Paragraph("Created on: " + formatter.format(request.getCreatedAt())));
                if (request.getCompletedAt() != null) {
                    document.add(new Paragraph("Completed on: " + formatter.format(request.getCompletedAt())));
                }
                document.add(new Paragraph("Generated on: " + formatter.format(LocalDateTime.now())));
                document.add(Chunk.NEWLINE);

                Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
                document.add(new Paragraph("Customer: " + request.getCustomerFirstName() + " " + request.getCustomerLastName(), infoFont));
                document.add(new Paragraph("Phone: " + request.getCustomerPhone(), infoFont));
                document.add(new Paragraph("Car: " + request.getCarBrand() + " " + request.getCarModel(), infoFont));
                if (request.getMechanicFirstName() != null) {
                    document.add(new Paragraph("Mechanic: " + request.getMechanicFirstName() + " " + request.getMechanicLastName(), infoFont));
                }
                document.add(Chunk.NEWLINE);

                if (request.getUsedParts() != null && !request.getUsedParts().isEmpty()) {
                    PdfPTable table = new PdfPTable(4);
                    table.setWidthPercentage(100);
                    table.setWidths(new int[]{4, 2, 2, 2});

                    addTableHeader(table, "Part", "Quantity", "Unit Price", "Total Price");

                    for (UsedPartRequest part : request.getUsedParts()) {
                        addTableRow(table,
                                part.getPartName(),
                                String.valueOf(part.getQuantity()),
                                part.getUnitPrice().toPlainString() + " BGN",
                                part.getTotalPrice().toPlainString() + " BGN"
                        );
                    }

                    document.add(table);
                    document.add(Chunk.NEWLINE);
                }

                Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
                PdfPTable priceTable = new PdfPTable(2);
                priceTable.setWidthPercentage(60);
                priceTable.setWidths(new int[]{3, 2});
                priceTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
                priceTable.setSpacingBefore(10);

                addPriceRow(priceTable, "Parts Total:", request.getPartsTotal().toPlainString() + " BGN", infoFont);
                addPriceRow(priceTable, "Service Fee:", request.getServiceFee().toPlainString() + " BGN", infoFont);
                addPriceRow(priceTable, "Grand Total:", request.getTotalPrice().toPlainString() + " BGN", totalFont);
                priceTable.setSpacingAfter(10);
                document.add(priceTable);

                document.add(Chunk.NEWLINE);
                document.add(new Paragraph("Thank you for choosing our garage!", infoFont));

                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] pdfBytes = outputStream.toByteArray();

            InvoiceLog invoiceLog = DtoMapper.mapInvoiceRequestToInvoiceLog(request);
            invoiceLog.setDocument(pdfBytes);
            invoiceLogRepository.save(invoiceLog);

            return pdfBytes;
        }

        private void addTableHeader(PdfPTable table, String... headers) {

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
        }

        private void addTableRow(PdfPTable table, String... values) {

            for (String value : values) {
                PdfPCell cell = new PdfPCell(new Phrase(value));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }
        }

        private void addPriceRow(PdfPTable table, String label, String value, Font font) {

            PdfPCell labelCell = new PdfPCell(new Phrase(label, font));
            labelCell.setBorder(Rectangle.NO_BORDER);
            PdfPCell valueCell = new PdfPCell(new Phrase(value, font));
            valueCell.setBorder(Rectangle.NO_BORDER);
            table.addCell(labelCell);
            table.addCell(valueCell);
        }
    }
