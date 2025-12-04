package com.softuni.gms.service;

import com.softuni.gms.model.MessageLog;
import com.softuni.gms.repository.MessageLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageLogServiceUTest {

    @Mock
    private MessageLogRepository messageLogRepository;

    @InjectMocks
    private MessageLogService messageLogService;

    @Test
    void testLogMessage_savesCorrectMessageLog() {

        String recipient = "0888123456";
        String content = "Your repair is complete!";
        String channel = "WHATSAPP";
        String status = "SUCCESS";
        String response = "OK";
        String error = null;

        messageLogService.logMessage(recipient, content, channel, status, response, error);

        ArgumentCaptor<MessageLog> captor = ArgumentCaptor.forClass(MessageLog.class);
        verify(messageLogRepository).save(captor.capture());

        MessageLog saved = captor.getValue();

        Assertions.assertEquals(recipient, saved.getRecipient());
        Assertions.assertEquals(content, saved.getMessageContent());
        Assertions.assertEquals(channel, saved.getChannel());
        Assertions.assertEquals(status, saved.getStatus());
        Assertions.assertEquals(response, saved.getResponse());
        Assertions.assertNull(saved.getError());

        Assertions.assertNotNull(saved.getTimestamp());
        Assertions.assertTrue(saved.getTimestamp().isBefore(LocalDateTime.now().plusSeconds(1)));
        Assertions.assertTrue(saved.getTimestamp().isAfter(LocalDateTime.now().minusSeconds(5)));
    }
}
