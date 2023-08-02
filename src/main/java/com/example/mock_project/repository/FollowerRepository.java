package com.example.mock_project.repository;

import com.example.mock_project.entity.FollowerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<FollowerUserEntity, Long> {
    @Query("SELECT f FROM FollowerUserEntity as f where f.followee.id = :followee")
    Optional<FollowerUserEntity> findByFollows (@Param("followee") Long followee);

    @Query("SELECT f FROM FollowerUserEntity as f where f.followee.id = :followee and f.follower.id=:follower")
    FollowerUserEntity findByFollowsAndFollower (@Param("followee") Long followee,@Param("follower") Long follower);

    @Modifying
    @Query("update FollowerUserEntity f set f.unfollow = :unfollow  where f.id = :id")
    void updateByUnfollow (@Param("id") Long id,@Param("unfollow") Integer unfollow);

}
