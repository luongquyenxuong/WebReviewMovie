package com.example.mock_project.service.impl;

import com.example.mock_project.constant.ConstantNotificationUtils;
import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.constant.NotificationType;
import com.example.mock_project.constant.WebsocketAction;
import com.example.mock_project.dto.BaseResponse;

import com.example.mock_project.dto.NotificationDto;
import com.example.mock_project.entity.*;
import com.example.mock_project.exception.BaseException;
import com.example.mock_project.mapper.NotificationMapper;
import com.example.mock_project.repository.NotificationRepository;
import com.example.mock_project.service.NotificationService;
import com.example.mock_project.service.UserService;
import com.example.mock_project.service.WebSocketService;
import com.example.mock_project.util.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class NotificationImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    UserService userService;


    @Override
    public BaseResponse<Void> createNotificationPost(UserEntity user, Long sourceId) {
//        log.info("saveNotificationPost: user {},sourceId {} :", user.toString(), sourceId);


        //Set thong tin notificationEntity
        NotificationEntity entity = setNotificationPostEntity(user.getId(), user.getUsername(), sourceId);

        //Luu vao bang notification
        NotificationEntity notification = notificationRepository.save(entity);

        // send notification for author
        webSocketService.send(
                user.getUsername(),
                "/queue/notification",
                notificationMapper.toDto(notification),
                WebsocketAction.CONFIRM_POST
        );


        List<FollowerUserEntity> followerUserEntityList = user.getFolloweeUserList();
        for (FollowerUserEntity followee : followerUserEntityList) {
            NotificationEntity entityFollowee =
                    setNotificationFolloweeEntity(
                            followee.getFollower().getId(),

                            user.getFullname(), sourceId);
            NotificationEntity notificationFollowee = notificationRepository.save(entityFollowee);
            webSocketService.send(followee.getFollower().getUsername(),
                    "/queue/notification",
                    notificationMapper.toDto(notificationFollowee),
                    WebsocketAction.NOTIFY_NEW_POST
            );
        }


        return BaseResponse.<Void>builder()
                .status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS)
                .message("Created notification post successfully")
                .build();


    }

    //Set entity notification của chủ post de luu
    private NotificationEntity setNotificationPostEntity(Long userId, String username, Long sourceId) {
        return NotificationEntity.builder()
                .title(ConstantNotificationUtils.POST_CONFIRM_TITLE_NOTIFICATION)
                .message(ConstantNotificationUtils.POST_CONFIRM_MESSAGE_NOTIFICATION)
                .typeId(NotificationType.POST.getId())
                .sourceId(sourceId)
                .isRead(0)
                .createdAt(DateHelper.currentDateTime())
                .user(UserEntity.builder().id(userId).username(username).build())
                .build();
    }

    //Set entity notification followee của chủ post de luu
    private NotificationEntity setNotificationFolloweeEntity(Long userId, String name, Long sourceId) {
        return NotificationEntity.builder()
                .title(ConstantNotificationUtils.POST_FOLLOWEE_TITLE_NOTIFICATION)
                .message(name + ConstantNotificationUtils.POST_FOLLOWEE_MESSAGE_NOTIFICATION)
                .typeId(NotificationType.POST.getId())
                .sourceId(sourceId)
                .isRead(0)
                .createdAt(DateHelper.currentDateTime())
                .user(UserEntity.builder().id(userId).build())
                .build();
    }

    //Set entity notification cua comment de luu
//    private NotificationEntity setNotificationCommentEntity(Long userId) {
//        return NotificationEntity.builder()
//                .title(ConstantNotificationUtils.COMMENT_TITLE_NOTIFICATION)
//                .message(ConstantNotificationUtils.COMMENT_MESSAGE_CONTENT_NOTIFICATION)
//                .isRead(0)
//                .createdAt(DateHelper.currentDateTime())
//                .user(UserEntity.builder().id(userId).build())
//                .build();
//    }


    @Override
    public BaseResponse<Page<NotificationDto>> getNotificationList() {
        BaseResponse<UserEntity> responseUser = userService.getUser();
        if (responseUser.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(responseUser.getStatus(), responseUser.getMessage());
        }
        UserEntity user = responseUser.getData();
        Pageable page = PageRequest.of(0, 3);
        Page<NotificationEntity> notificationEntityPage =
                notificationRepository.getNotificationList(user.getId(), ConstantNotificationUtils.UNREAD_NOTIFICATION, page);
        if (notificationEntityPage.isEmpty()) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, "Can't get list notification");
        }
        Page<NotificationDto> lst = notificationEntityPage.map(notificationMapper::toDto);


        return BaseResponse.<Page<NotificationDto>>builder()
                .status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS)
                .message("Get list notification successfully")
                .data(lst)
                .build();
    }

    @Override
    public BaseResponse<List<NotificationDto>> getNotificationListAll() {
        BaseResponse<UserEntity> responseUser = userService.getUser();
        if (responseUser.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(responseUser.getStatus(), responseUser.getMessage());
        }
        UserEntity user = responseUser.getData();

        List<NotificationEntity> notificationEntityPage =
                notificationRepository.getNotificationListAll(user.getId(), ConstantNotificationUtils.UNREAD_NOTIFICATION);
        if (notificationEntityPage.isEmpty()) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, "Can't get list notification");
        }
        List<NotificationDto> lst = notificationEntityPage.stream().map(notificationMapper::toDto).collect(Collectors.toList());


        return BaseResponse.<List<NotificationDto>>builder()
                .status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS)
                .message("Get list notification successfully")
                .data(lst)
                .build();
    }


}
