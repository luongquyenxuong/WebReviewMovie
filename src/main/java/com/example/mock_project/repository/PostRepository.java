package com.example.mock_project.repository;

import com.example.mock_project.dto.ConditionPost;
import com.example.mock_project.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<PostEntity, Long> {
//    @Query("UPDATE PostEntity p SET p.status = :status WHERE p.id = :id")
//    Optional<Integer> updateByStatus(@Param("id") Long id,
//                                     @Param("status") int status);

    @Query("SELECT p from PostEntity AS p WHERE p.title LIKE :#{#condition.title} " +
            "AND p.user.fullname LIKE :#{#condition.author} " +
            "AND p.status= :#{#condition.status} " +
            "ORDER BY p.createdAt DESC ")
    Page<PostEntity> getAllByConditionAndStatus(@Param("condition") ConditionPost conditionPost,Pageable pageable);

    @Query("SELECT p FROM PostEntity AS p WHERE p.title LIKE :#{#condition.title} " +
            "AND p.user.fullname LIKE :#{#condition.author} " +
            "ORDER BY p.createdAt DESC ")
    Page<PostEntity> getAllByCondition(@Param("condition") ConditionPost conditionPost, Pageable pageable);

    @Query("SELECT p FROM PostEntity AS p WHERE p.title LIKE :#{#condition.title} " +
            "AND p.user.fullname LIKE :#{#condition.author} " +
            "AND p.createdAt BETWEEN :dateFrom AND :dateTo " +
            "AND p.status =:#{#condition.status} " +
            "ORDER BY p.createdAt DESC ")
    Page<PostEntity> getAllByConditionTime(@Param("condition") ConditionPost conditionPost, Date dateFrom,Date dateTo, Pageable pageable);

    @Query("SELECT p FROM PostEntity AS p WHERE p.title LIKE :#{#condition.title} " +
            "AND p.user.fullname LIKE :#{#condition.author} " +
            "AND p.createdAt BETWEEN :dateFrom AND :dateTo " +
            "AND p.user.id=:userId " +
            "ORDER BY p.createdAt DESC ")
    Page<PostEntity> getAllPostProfile(@Param("condition") ConditionPost conditionPost,@Param("userId") Long userId ,Date dateFrom,Date dateTo, Pageable pageable);



}
