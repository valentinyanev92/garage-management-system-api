package com.softuni.gms.app.web;

import com.softuni.gms.app.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @Autowired
    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(
            @RequestParam String phone,
            @RequestParam String message) {

        whatsAppService.sendWhatsAppMessage(phone, message);
        return ResponseEntity.ok("Message sent to: " + phone);
    }
}
