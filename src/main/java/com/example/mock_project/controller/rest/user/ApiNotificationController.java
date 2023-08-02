package com.example.mock_project.controller.rest.user;


import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.NotificationDto;
import com.example.mock_project.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/api/notification")
public class ApiNotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/get-my-list")
    public ResponseEntity<?> getMyNotificationList() {

        BaseResponse<Page<NotificationDto>> baseResponse = notificationService.getNotificationList();
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/get-my-list-all")
    public ResponseEntity<?> getMyNotificationListAll() {

        BaseResponse<List<NotificationDto>> baseResponse = notificationService.getNotificationListAll();
        return ResponseEntity.ok(baseResponse);
    }
}
