package com.example.mock_project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto implements Serializable {
    private Long id;

    private String fullname;

    private String username;

    private String email;

    private String password;

    private String phone;

    private String roleName;

    private Long roleId;

    private List<PostDto> myPostList;

    private List<UserDto> followerList;

    private List<UserDto> followeeList;

    private List<PostDto> postArchive;


}
