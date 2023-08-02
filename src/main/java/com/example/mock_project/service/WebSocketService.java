package com.example.mock_project.service;

import com.example.mock_project.constant.WebsocketAction;
import com.example.mock_project.dto.WebsocketResponse;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {


    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public <T> void send(String user, String destination, T message, WebsocketAction topic) {
        this.simpMessagingTemplate.convertAndSendToUser(user, destination, WebsocketResponse.build(message, topic));
    }

    public <T> void broadcast(String destination, T message) {
        this.simpMessagingTemplate.convertAndSend(destination, message);
    }
}
