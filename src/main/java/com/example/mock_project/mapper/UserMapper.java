package com.example.mock_project.mapper;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.dto.UserDto;
import com.example.mock_project.entity.PostEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

@Autowired
FollowMapper followMapper;

    public UserDto toDto(UserEntity entity) {
        var role = entity.getRoles().stream().findFirst();
        if (role.isEmpty()){
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED,"Not found role user");
        }
        return UserDto.builder()
                .id(entity.getId())
                .fullname(entity.getFullname())
                .username(entity.getUsername())
                .phone(entity.getPhone())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .roleName(role.get().getName())
                .roleId(role.get().getId())
                //Nguoi theo doi minh
                .followerList(
                        entity
                                .getFollowerUserList()
                                .stream()
                                .map(followerEntity -> UserDto.builder()
                                        .id(followerEntity.getFollower().getId())
                                        .fullname(followerEntity.getFollower().getFullname())
                                        .build()).collect(Collectors.toList())
                )
                //Nguoi duoc minh theo doi
                .followeeList(
                        entity
                                .getFolloweeUserList()
                                .stream()
                                .map(followerEntity -> UserDto.builder()
                                        .id(followerEntity.getFollowee().getId())
                                        .fullname(followerEntity.getFollowee().getFullname())
                                        .build()).collect(Collectors.toList()))

                .build();
    }

    public UserEntity toEntity(UserDto dto) {

        UserEntity entity = new UserEntity();
//        Set<PostEntity> postArchive = dto.getPostArchive()
//                .stream()
//                .map(postDto -> PostEntity.builder().id(postDto.getId()).build()).collect(Collectors.toSet());
//        entity.setId(dto.getId());
//        entity.setFullname(dto.getFullname());
//        entity.setUsername(dto.getUsername());
//        entity.setPhone(dto.getPhone());
//        entity.setPassword(dto.getPassword());
//        entity.setEmail(dto.getEmail());


        return entity;
    }
}
