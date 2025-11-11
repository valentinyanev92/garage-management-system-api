package com.softuni.gms.app.service;

import com.softuni.gms.app.config.GreenApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
public class WhatsAppService {

    private final GreenApiProperties props;
    private final MessageLogService messageLogService;

    @Autowired
    public WhatsAppService(GreenApiProperties props, MessageLogService messageLogService) {
        this.props = props;
        this.messageLogService = messageLogService;
    }

    public void sendWhatsAppMessage(String phoneNumber, String message) {

        String chatId = phoneNumber + "@c.us";

        Map<String, Object> payload = Map.of(
                "chatId", chatId,
                "message", message
        );

        String url = props.getBaseUrl()
                + "/waInstance" + props.getIdInstance()
                + "/SendMessage/" + props.getTokenInstance();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "PostmanRuntime/7.36.0");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Message send! Response from GreenApi: {}", response.getBody());
            messageLogService.logMessage(phoneNumber, message, "WHATSAPP", "SENT", response.getBody(), null);
        } catch (Exception ex) {
            log.error("Error while sending the message to GreenApi: {}", ex.getMessage());
            messageLogService.logMessage(phoneNumber, message, "WHATSAPP", "FAILED", null, ex.getMessage());
        }
    }
}
