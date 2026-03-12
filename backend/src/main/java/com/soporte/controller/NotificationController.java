package com.soporte.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.soporte.model.Solicitud;

@Controller
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/notify")
    @SendTo("/topic/alerts")
    public Solicitud send(Solicitud solicitud) {
        return solicitud;
    }
}