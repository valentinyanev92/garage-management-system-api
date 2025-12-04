package com.softuni.gms.service;

import com.softuni.gms.config.GreenApiProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WhatsAppServiceUTest {

    @Mock
    private GreenApiProperties props;

    @Mock
    private MessageLogService messageLogService;

    @Test
    void testSendWhatsAppMessage_success() {

        when(props.getBaseUrl()).thenReturn("https://api.green-api.com");
        when(props.getIdInstance()).thenReturn("1234");
        when(props.getTokenInstance()).thenReturn("abcd");

        try (MockedConstruction<org.springframework.web.client.RestTemplate> mocked =
                     Mockito.mockConstruction(org.springframework.web.client.RestTemplate.class,
                             (restMock, context) -> {

                                 ResponseEntity<String> fakeResponse =
                                         new ResponseEntity<>("{\"status\":\"ok\"}", HttpStatus.OK);

                                 when(restMock.postForEntity(anyString(), any(), eq(String.class)))
                                         .thenReturn(fakeResponse);
                             })) {

            WhatsAppService service = new WhatsAppService(props, messageLogService);

            service.sendWhatsAppMessage("0888123456", "Hello!");

            verify(messageLogService, times(1)).logMessage(
                    eq("0888123456"),
                    eq("Hello!"),
                    eq("WHATSAPP"),
                    eq("SENT"),
                    anyString(),
                    isNull()
            );

            Assertions.assertEquals(1, mocked.constructed().size());
        }
    }

//    @Test
//    void testSendWhatsAppMessage_failure() {
//
//        when(props.getBaseUrl()).thenReturn("https://api.green-api.com");
//        when(props.getIdInstance()).thenReturn("1234");
//        when(props.getTokenInstance()).thenReturn("abcd");
//
//        try (MockedConstruction<org.springframework.web.client.RestTemplate> mocked =
//                     Mockito.mockConstruction(org.springframework.web.client.RestTemplate.class,
//                             (restMock, context) -> {
//
//                                 when(restMock.postForEntity(anyString(), any(), eq(String.class)))
//                                         .thenThrow(new RuntimeException("API DOWN"));
//                             })) {
//
//            WhatsAppService service = new WhatsAppService(props, messageLogService);
//
//            service.sendWhatsAppMessage("0888123456", "Hello!");
//
//            verify(messageLogService, times(1)).logMessage(
//                    eq("0888123456"),
//                    eq("Hello!"),
//                    eq("WHATSAPP"),
//                    eq("FAILED"),
//                    isNull(),
//                    eq("API DOWN")
//            );
//
//            Assertions.assertEquals(1, mocked.constructed().size());
//        }
//    }
}
