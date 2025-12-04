package com.softuni.gms.kafka;

import com.softuni.gms.model.RepairLog;
import com.softuni.gms.repository.RepairLogRepository;
import com.softuni.gms.shared.kafka.dto.RepairKafkaEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RepairEventConsumer {

    private final RepairLogRepository repairLogRepository;

    @KafkaListener(topics = "gms-events", groupId = "gms-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(RepairKafkaEventRequest event) {

        RepairLog repairLog = new RepairLog();
        repairLog.setRepairId(String.valueOf(event.getRepairOrderId()));
        repairLog.setMessage(event.getMessage());
        repairLog.setStatus(event.getNewStatus());
        repairLog.setReceivedAt(LocalDateTime.now());
        repairLogRepository.save(repairLog);
    }
}
