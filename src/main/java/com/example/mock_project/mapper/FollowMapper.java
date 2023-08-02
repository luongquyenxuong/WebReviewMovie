package com.example.mock_project.mapper;

import com.example.mock_project.dto.FollowerDto;
import com.example.mock_project.entity.FollowerUserEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.util.DateHelper;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
//    public FollowerDto toDto(FollowerUserEntity entity) {
//        return FollowerDto.builder()
//                .id(entity.getId())
//                .createdAt(DateHelper.format(entity.getCreatedAt()))
//                .unfollow(entity.getUnfollow())
//                .followerId(entity.getFollower().getId())
//                .followerName(entity.getFollower().getFullname())
//                .followeeId(entity.getFollowee().getId())
//                .followeeName(entity.getFollowee().getFullname())
//                .build();
//    }
//
//    public FollowerUserEntity toEntity(FollowerDto dto) {
//
//        FollowerUserEntity entity = new FollowerUserEntity();
//        entity.setId(dto.getId());
//        entity.setCreatedAt(DateHelper.convert(dto.getCreatedAt()));
//        entity.setUnfollow(dto.getUnfollow());
//        entity.setFollower(UserEntity.builder().id(dto.getFollowerId()).build());
//        entity.setFollowee(UserEntity.builder().id(dto.getFolloweeId()).build());
//
//
//        return entity;
//    }


}
