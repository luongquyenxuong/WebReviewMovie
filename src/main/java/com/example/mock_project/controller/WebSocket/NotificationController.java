package com.example.mock_project.controller.WebSocket;

import com.example.mock_project.dto.Greeting;
import com.example.mock_project.dto.HelloMessage;
import com.example.mock_project.dto.NotificationDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class NotificationController {



    @MessageMapping("/notification")
    @SendTo("/topic/notification")
    public NotificationDto notification(NotificationDto notificationDto) throws Exception {
        Thread.sleep(1000); // simulated delay
        return notificationDto;
    }
}
