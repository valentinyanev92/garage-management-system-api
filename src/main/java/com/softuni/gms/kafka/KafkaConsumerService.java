package com.softuni.gms.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "gms-events", groupId = "gms-group")
    public void listen(String message) {

    }
}
