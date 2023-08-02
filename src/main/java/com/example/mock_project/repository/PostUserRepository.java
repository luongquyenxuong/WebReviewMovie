package com.example.mock_project.repository;

import com.example.mock_project.dto.ConditionPost;
import com.example.mock_project.entity.PostEntity;
import com.example.mock_project.entity.PostUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostUserRepository extends JpaRepository<PostUserEntity, Long> {
    @Query("SELECT pr FROM PostUserEntity as p join  PostEntity as pr on p.postId=pr.id" +
            " where p.userId = :userId " +
            " and p.isArchive = :isArchive")
    Page<PostEntity> getAllByUserIdAndIsArchive(@Param("userId") Long userId,@Param("isArchive") Integer isArchive, Pageable pageable);

    @Query("SELECT p FROM PostUserEntity as p" +
            " where p.userId = :userId " +
            "and p.postId=:postId ")
    Optional<PostUserEntity> findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);
}
