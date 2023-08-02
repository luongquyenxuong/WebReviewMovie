package com.example.mock_project.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class NotificationDto {
    private Long id;
    private String title;
    private String message;
    private Long typeId;
    private String typeName;
    private Long sourceId;
    private String createdAt;
    private String isRead;
    private Integer isReadInteger;
    private Long userId;
}
