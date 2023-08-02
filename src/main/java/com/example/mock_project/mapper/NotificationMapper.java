package com.example.mock_project.mapper;

import com.example.mock_project.constant.ConstantNotificationUtils;
import com.example.mock_project.constant.NotificationType;
import com.example.mock_project.dto.NotificationDto;
import com.example.mock_project.entity.FollowerUserEntity;
import com.example.mock_project.entity.NotificationEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.util.DateHelper;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDto toDto(NotificationEntity entity){
        return NotificationDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .message(entity.getMessage())
                .createdAt(DateHelper.format(entity.getCreatedAt()))
                .typeId(entity.getTypeId())
                .typeName(entity.getTypeId().equals(NotificationType.POST.getId())?"POST":"COMMENT")
                .sourceId(entity.getSourceId())
                .isReadInteger(entity.getIsRead())
                .isRead(entity.getIsRead()== ConstantNotificationUtils.UNREAD_NOTIFICATION?"Chưa đọc":"Đã đọc")
                .userId(entity.getUser().getId())
                .build();
    }

    public NotificationEntity toEntity(NotificationDto dto){
        NotificationEntity entity = new NotificationEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setMessage(dto.getMessage());
        entity.setCreatedAt(DateHelper.convert(dto.getCreatedAt()));
        entity.setIsRead(dto.getIsReadInteger());
        entity.setUser(UserEntity.builder().id(dto.getUserId()).build());
        return entity;
    }
}
