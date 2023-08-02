package com.example.mock_project.service;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.NotificationDto;
import com.example.mock_project.entity.NotificationEntity;
import com.example.mock_project.entity.UserEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NotificationService {

    BaseResponse<Void> createNotificationPost( UserEntity user, Long sourceId);

    BaseResponse<Page<NotificationDto>> getNotificationList();

    BaseResponse<List<NotificationDto>> getNotificationListAll();


}
