package com.softuni.gms.app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softuni.gms.app.service.WhatsAppService;
import com.softuni.gms.app.web.dto.RepairCompletionRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WhatsAppController.class)
class WhatsAppControllerApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WhatsAppService whatsAppService;

    private RepairCompletionRequest buildValidRequest() {
        return RepairCompletionRequest.builder()
                .carBrand("BMW")
                .carModel("E46")
                .mechanicFistName("Ivan")
                .mechanicLastName("Ivanov")
                .phoneNumber("0888123456")
                .build();
    }

    @Test
    void testSendMessage_success() throws Exception {

        RepairCompletionRequest req = buildValidRequest();

        mockMvc.perform(post("/whatsapp/complete-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());

        Mockito.verify(whatsAppService, Mockito.times(1))
                .sendWhatsAppMessage(
                        Mockito.eq(req.getPhoneNumber()),
                        Mockito.anyString()  // текстът от mapper
                );
    }

    @Test
    void testSendMessage_invalid_shouldReturn400() throws Exception {

        RepairCompletionRequest invalid = new RepairCompletionRequest();

        mockMvc.perform(post("/whatsapp/complete-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(whatsAppService);
    }
}
