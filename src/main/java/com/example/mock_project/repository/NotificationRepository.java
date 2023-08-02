package com.example.mock_project.repository;

import com.example.mock_project.entity.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n " +
            "FROM NotificationEntity AS n " +
            "WHERE n.user.id=:userId and n.isRead=:isRead " +
            "ORDER BY n.createdAt DESC ")
    Page<NotificationEntity> getNotificationList(@Param("userId") Long userId, @Param("isRead") Integer isRead, Pageable page);

    @Query("SELECT n " +
            "FROM NotificationEntity AS n " +
            "WHERE n.user.id=:userId and n.isRead=:isRead " +
            "ORDER BY n.createdAt DESC ")
    List<NotificationEntity> getNotificationListAll(@Param("userId") Long userId, @Param("isRead") Integer isRead);
}
