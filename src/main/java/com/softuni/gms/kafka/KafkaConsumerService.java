package com.softuni.gms.kafka;

import com.softuni.gms.service.WhatsAppService;
import com.softuni.gms.shared.kafka.dto.RepairKafkaEventRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    private final WhatsAppService whatsAppService;

    @Autowired
    public KafkaConsumerService(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @KafkaListener(topics = "gms-events", groupId = "gms-group")
    public void listen(RepairKafkaEventRequest event) {

        log.info("Received RepairKafkaEventRequest {}", event.toString());
        whatsAppService.sendWhatsAppMessage(event.getPhoneNumber(), event.getMessage());
    }
}
