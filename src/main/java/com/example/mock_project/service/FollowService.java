package com.example.mock_project.service;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.FollowerDto;
import org.apache.catalina.User;


public interface FollowService {

    BaseResponse<Void> changeFollow(Long idFollowee);

    BaseResponse<Void> isFollowed(Long followeeId);


}
