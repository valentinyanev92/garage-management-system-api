package com.softuni.gms.app.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageLog {

    @Id
    private String id;

    private String recipient;
    private String messageContent;
    private String channel;
    private String status;
    private String response;
    private String error;
    private LocalDateTime timestamp;
}
