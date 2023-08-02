package com.example.mock_project.mapper;

import com.example.mock_project.dto.PostDto;
import com.example.mock_project.dto.UserDto;
import com.example.mock_project.entity.PostEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.util.DateHelper;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDto toDto(PostEntity entity) {
        return PostDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .imagePost(entity.getImagePost())
                .status(entity.getStatus() == 1 ? "Đã duyệt" : "Chưa duyệt")
                .statusInt(entity.getStatus())
                .userId(entity.getUser().getId())
                .fullNameUser(entity.getUser().getFullname())
                .createdAt(DateHelper.format(entity.getCreatedAt()))
                .build();
    }

    public PostEntity toEntity(PostDto dto) {
        PostEntity entity = new PostEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setImagePost(dto.getImagePost());
        entity.setStatus(dto.getStatusInt());
        entity.setCreatedAt(DateHelper.convert(dto.getCreatedAt()));
        entity.setUser(UserEntity.builder().id(dto.getUserId()).build());

        return entity;
    }
}
