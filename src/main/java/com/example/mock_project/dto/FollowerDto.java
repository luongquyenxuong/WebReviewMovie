package com.example.mock_project.dto;

import com.example.mock_project.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FollowerDto implements Serializable {

    private Long id;

    private Integer unfollow;

    private String createdAt;

    private Long followerId;

    private String followerName;

    private Long followeeId;

    private String followeeName;
}
