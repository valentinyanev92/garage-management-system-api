package com.softuni.gms.service;

import com.softuni.gms.model.Notification;
import com.softuni.gms.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification sendNotification(Notification message) {
        message.setSentAt(LocalDateTime.now());
        return notificationRepository.save(message);
    }

}
