package com.softuni.gms.app.web;

import com.softuni.gms.app.service.WhatsAppService;
import com.softuni.gms.app.web.dto.RepairCompletionRequest;
import com.softuni.gms.app.web.mapper.DtoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    @Autowired
    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/complete-order")
    public ResponseEntity<String> sendMessageForCompletedRepairOrder(@RequestBody @Valid RepairCompletionRequest request, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().build();
        }

        whatsAppService.sendWhatsAppMessage(request.getPhoneNumber(), DtoMapper.repairCompletionRequestToString(request));
        return ResponseEntity.ok().build();
    }
}
