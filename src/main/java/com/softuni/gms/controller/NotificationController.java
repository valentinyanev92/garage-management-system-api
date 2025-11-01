//package com.softuni.gms.controller;
//
//import com.softuni.gms.model.Notification;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/message")
//public class NotificationController {
//
//    private final NotificationController notificationController;
//
//    @Autowired
//    public NotificationController(NotificationController notificationController) {
//        this.notificationController = notificationController;
//    }
//
//    @PostMapping("/send")
//    public ResponseEntity<Notification> send(@RequestBody Notification notification) {
//
//        notificationController.send(notification);
//        return ResponseEntity.ok(notification);
//    }
//}
