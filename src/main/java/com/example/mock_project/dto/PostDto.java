package com.example.mock_project.dto;

import com.example.mock_project.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDto implements Serializable {
    private Long id;

    private String title;
    private String content;
    private String imagePost;
    private String status;
    private Integer statusInt;
    private String createdAt;
    private String fullNameUser;
    private Long userId;


}
