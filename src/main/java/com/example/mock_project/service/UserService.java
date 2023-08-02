package com.example.mock_project.service;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.UserDto;
import com.example.mock_project.entity.UserEntity;

import java.util.List;

public interface UserService {
    BaseResponse<Void> saveUser(UserEntity user);

    BaseResponse<List<String>> login(String username, String password);

     List<UserDto> getAll();

    BaseResponse<UserEntity> getUser();
    BaseResponse<Void> deleted(Long id);


}
