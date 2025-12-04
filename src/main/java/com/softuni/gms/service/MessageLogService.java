package com.softuni.gms.service;

import com.softuni.gms.model.MessageLog;
import com.softuni.gms.repository.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageLogService {

    private final MessageLogRepository messageLogRepository;

    public void logMessage(String recipient,
                           String messageContent,
                           String channel,
                           String status,
                           String response,
                           String error) {

        MessageLog messageLog = MessageLog.builder()
                .recipient(recipient)
                .messageContent(messageContent)
                .channel(channel)
                .status(status)
                .response(response)
                .error(error)
                .timestamp(LocalDateTime.now())
                .build();

        log.info("Saving MessageLog : {}", messageLog.toString());
        messageLogRepository.save(messageLog);
    }
}
