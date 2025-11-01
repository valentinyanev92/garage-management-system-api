package com.softuni.gms.service;

import com.softuni.gms.config.GreenApiProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class WhatsAppService {

    private final GreenApiProperties props;

    public WhatsAppService(GreenApiProperties props) {
        this.props = props;
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
            System.out.println("Green API response: " + response.getBody());
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }
}
