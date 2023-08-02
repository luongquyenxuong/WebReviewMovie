package com.example.mock_project.repository;

import com.example.mock_project.entity.RoleEntity;
import com.example.mock_project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity as u where u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

}
