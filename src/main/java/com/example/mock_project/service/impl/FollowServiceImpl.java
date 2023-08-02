package com.example.mock_project.service.impl;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.constant.FollowStatus;
import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.entity.FollowerUserEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.exception.BaseException;
import com.example.mock_project.repository.FollowerRepository;

import com.example.mock_project.repository.UserRepository;

import com.example.mock_project.service.FollowService;
import com.example.mock_project.service.UserService;
import com.example.mock_project.util.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowerRepository followerRepository;

//    @Autowired
//    private FollowMapper followMapper;

    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public BaseResponse<Void> changeFollow(Long followeeId) {//idFollowers: người theo dõi ,idFollowee:nguoi duoc theo doi


        BaseResponse<UserEntity> userFollower = userService.getUser();
        if (userFollower.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(userFollower.getStatus(), userFollower.getMessage());
        }

        UserEntity userFollowee = userRepository
                .findById(followeeId)
                .orElseThrow(() -> new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, "Not found user followee"));


        FollowerUserEntity followerUserEntity = getFollowEntity(followeeId, userFollower.getData().getId());

        //Kiem tra co data trong csdl chua
        //Neu chua thi add vao csdl
        if (followerUserEntity == null) {
            saveFollow(userFollower.getData(), userFollowee);
        } else {

            if (followerUserEntity.getUnfollow() == 0) {
                followerRepository.updateByUnfollow(followerUserEntity.getId(), 1);
            } else {
                followerRepository.updateByUnfollow(followerUserEntity.getId(), 0);
            }

        }
        return BaseResponse.<Void>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Update Successfully").build();
    }

    @Transactional
    @Override
    public BaseResponse<Void> isFollowed(Long followeeId) {
        BaseResponse<UserEntity> BaseUserResponse = userService.getUser();
        if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            return BaseResponse.<Void>builder().status(FollowStatus.USER_NOT_LOGIN.getStatus()).message("This is user not login").build();
        }
        UserEntity user = BaseUserResponse.getData();
        if (user.getId().equals(followeeId)) {
            return BaseResponse.<Void>builder().status(FollowStatus.USER.getStatus()).message("This is user").build();
        }
        FollowerUserEntity followerUserEntity = getFollowEntity(followeeId, user.getId());

        if (followerUserEntity != null) {
            if (followerUserEntity.getUnfollow().equals(ConstantUtils.UNFOLLOW_STATUS)) {
                return BaseResponse.<Void>builder().status(FollowStatus.HAVE_FOLLOW.getStatus()).message("Have follow").build();
            }
        }
        return BaseResponse.<Void>builder().status(FollowStatus.NOT_FOLLOW.getStatus()).message("No follow").build();
    }

    @Transactional
    public FollowerUserEntity getFollowEntity(Long followeeId, Long followerId) {
        return followerRepository.findByFollowsAndFollower(followeeId, followerId);
    }

    private void saveFollow(UserEntity userFollower, UserEntity userFollowee) {
        FollowerUserEntity followerUserEntity = new FollowerUserEntity();
        String currentTimeString = DateHelper.currentDateTimeString();  // Create a date object

        followerUserEntity.setUnfollow(0);
        followerUserEntity.setCreatedAt(DateHelper.convert(currentTimeString));
        followerUserEntity.setFollower(userFollower);
        followerUserEntity.setFollowee(userFollowee);
        followerRepository.save(followerUserEntity);
    }
}
