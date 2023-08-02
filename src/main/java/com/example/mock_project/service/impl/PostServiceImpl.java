package com.example.mock_project.service.impl;

import com.example.mock_project.constant.ArchiveStatus;
import com.example.mock_project.constant.ConstantUtils;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.ConditionPost;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.entity.PostEntity;
import com.example.mock_project.entity.PostUserEntity;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.exception.BaseException;
import com.example.mock_project.mapper.PostMapper;
import com.example.mock_project.repository.PostRepository;
import com.example.mock_project.repository.PostUserRepository;
import com.example.mock_project.service.NotificationService;
import com.example.mock_project.service.PostService;
import com.example.mock_project.service.UserService;
import com.example.mock_project.util.DateHelper;
import com.example.mock_project.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostUserRepository postUserRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;


    @Override
    public BaseResponse<PostDto> savePost(PostDto post, MultipartFile multipartFile) {
        //postId and image null
        //Update no image new
        if (multipartFile == null && post.getId() != null) {

            post.setImagePost(post.getImagePost());
            postRepository.save(postMapper.toEntity(post));

            return BaseResponse.<PostDto>builder()
                    .status("00")
                    .message("Update post successfully")
                    .build();
        }
        //Update co image new
        if (post.getId() != null) {
            BaseResponse<String> responseUpload = FileUtils.uploadFile(multipartFile, "post");

            if (responseUpload.getStatus().equals("00")) {
                //Update post
                post.setImagePost(responseUpload.getData());
                postRepository.save(postMapper.toEntity(post));

                return BaseResponse.<PostDto>builder()
                        .status("00")
                        .message("Update post successfully")
                        .build();
            }
        }
        //Create post
        if (post.getId() == null && multipartFile != null) {
            BaseResponse<String> responseUpload = FileUtils.uploadFile(multipartFile, "post");

            if (responseUpload.getStatus().equals("00")) {


                BaseResponse<UserEntity> BaseUserResponse = userService.getUser();
                if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
                    throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, BaseUserResponse.getMessage());
                }

                UserEntity user = BaseUserResponse.getData();

                post.setUserId(user.getId());
                post.setImagePost(responseUpload.getData());


                postRepository.save(postMapper.toEntity(post));

                return BaseResponse.<PostDto>builder()
                        .status("00")
                        .message("Create post successful")
                        .build();
            }


        }
        return BaseResponse.<PostDto>builder()
                .status("99")
                .message("error")
                .build();
    }

    @Override
    public BaseResponse<PostDto> findPost(Long postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException("99", "Not found post"));


        return BaseResponse.<PostDto>builder().status("00").data(postMapper.toDto(post)).message("Find post successfully").build();
    }


    @Override
    @Transactional
    public BaseResponse<Page<PostDto>> getPostListByCondition(ConditionPost conditionPost, Integer page) {
        log.info("getPostListByCondition: ConditionPost {} , page: {}", conditionPost.toString(), page);

        Pageable pageable = PageRequest.of(page, 6);
        Page<PostEntity> postEntityPage;

        if (conditionPost.getStatus().equals(ConstantUtils.POST_STATUS_ALL)) {
            //Lấy ra tất cả bài post
            postEntityPage = postRepository.getAllByCondition(conditionPost, pageable);


        } else {
            //Lấy ra tất cả bài post theo trạng thái
            postEntityPage = postRepository.getAllByConditionAndStatus(conditionPost, pageable);


        }
        Page<PostDto> lst = postEntityPage.map(postMapper::toDto);


        if (lst.isEmpty()) {
            return BaseResponse.<Page<PostDto>>builder().status(ConstantUtils.BASERESPONSE_STATUS_FAILDED).message("Not list post by condition").build();
        }

        return BaseResponse.<Page<PostDto>>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Get list post successfully by condition").data(lst).build();
    }

    @Override
    @Transactional
    public BaseResponse<Page<PostDto>> getPostListByConditionTime(ConditionPost conditionPost, Integer page) {
        log.info("getPostListByCondition: ConditionPost {} ,page: {}", conditionPost.toString(), page);

        Date dateFrom = DateHelper.convert(conditionPost.getFromDate());
        Date dateTo = DateHelper.convert(conditionPost.getToDate());

        Pageable pageable = PageRequest.of(page, 6);

        Page<PostEntity> postEntityPage = postRepository.getAllByConditionTime(conditionPost, dateFrom, dateTo, pageable);

        if (postEntityPage.isEmpty()) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, "Not found post list");
        }
        return responsePostDtoList(postEntityPage,pageable);

    }

    @Override
    public BaseResponse<Page<PostDto>> getPostListProfile(ConditionPost conditionPost, Integer page) {
        log.info("getPostListByCondition: ConditionPost {} ,page: {}", conditionPost.toString(), page);

        BaseResponse<UserEntity> BaseUserResponse = userService.getUser();
        if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, BaseUserResponse.getMessage());
        }
        UserEntity user = BaseUserResponse.getData();


        Date dateFrom = DateHelper.convert(conditionPost.getFromDate());
        Date dateTo = DateHelper.convert(conditionPost.getToDate());

        Pageable pageable = PageRequest.of(page, 6);

        Page<PostEntity> postEntityPage = postRepository.getAllPostProfile(conditionPost, user.getId(), dateFrom, dateTo, pageable);

        return responsePostDtoList(postEntityPage,pageable);


    }

    @Override
    public BaseResponse<Page<PostDto>> getPostListArchive(ConditionPost conditionPost, Integer page) {
        return null;
    }


    @Override
    @Transactional
    public BaseResponse<Void> deleted(Long id) {
        postRepository.findById(id)
                .orElseThrow(() -> new BaseException("99", "Not found post"));


        postRepository.deleteById(id);

        return BaseResponse.<Void>builder()
                .status("00")
                .message("Deleted post successfully")
                .build();
    }

    @Override
    @Transactional
    public BaseResponse<Void> updateStatusPost(Long postId, Integer status) {
        log.info("updateStatusPost: idPost {}, status {}", postId, status);


        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException("99", "Not found post"));
//
        if (status.equals(ConstantUtils.POST_STATUS_CONFIRM)) {
            notificationService.createNotificationPost(post.getUser(), post.getId());
        }


        post.setStatus(status);
        postRepository.save(post);

        return BaseResponse.<Void>builder()
                .status("00")
                .message(status.equals(ConstantUtils.POST_STATUS_CONFIRM)
                        ? "Confirm post successfully"
                        : "UnConfirm post successfully")
                .build();
    }

    @Override
    public BaseResponse<Void> saveArchivePost(Long postId) {
        BaseResponse<UserEntity> BaseUserResponse = userService.getUser();

        if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            return BaseResponse.<Void>builder().status(ArchiveStatus.USER_NOT_LOGIN.getStatus()).message("User not login").build();
        }

        UserEntity user = BaseUserResponse.getData();

        Optional<PostUserEntity> optionalPostUser = postUserRepository.findByUserIdAndPostId(user.getId(), postId);


        if (optionalPostUser.isEmpty()) {
            postUserRepository.save(
                    PostUserEntity.builder()
                            .postId(postId)
                            .userId(user.getId())
                            .isArchive(1)
                            .build()
            );
            return BaseResponse.<Void>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Save post archive successfully").build();
        }


        PostUserEntity postUser = optionalPostUser.get();

        if (postUser.getIsArchive().equals(1)) {

            postUser.setIsArchive(0);
            postUserRepository.save(postUser);
           return BaseResponse.<Void>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Save post archive successfully").build();

        }


        postUser.setIsArchive(1);
        postUserRepository.save(postUser);

        return BaseResponse.<Void>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Save post archive successfully").build();
    }

    @Override
    public BaseResponse<Void> isArchivePost(Long postId) {
        BaseResponse<UserEntity> userResponse = userService.getUser();
        if (userResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            return BaseResponse.<Void>builder().status(ArchiveStatus.USER_NOT_LOGIN.getStatus()).message("User not login").build();
        }


        UserEntity user = userResponse.getData();
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, "Not found post"));

        if (user.getId().equals(post.getUser().getId())) {
            return BaseResponse.<Void>builder().status(ArchiveStatus.USER.getStatus()).message("This is my post").build();
        }


        Optional<PostUserEntity> optionalPostUser = postUserRepository.findByUserIdAndPostId(user.getId(), postId);
        if (optionalPostUser.isEmpty()) {
            return BaseResponse.<Void>builder().status(ArchiveStatus.NOT_ARCHIVE.getStatus()).message("No archive").build();
        }
        PostUserEntity postUser = optionalPostUser.get();

        if (postUser.getIsArchive().equals(ConstantUtils.POST_USER_ARCHIVE)) {

            return BaseResponse.<Void>builder().status(ArchiveStatus.HAVE_ARCHIVE.getStatus()).message("Have archive").build();

        }


        return BaseResponse.<Void>builder().status(ArchiveStatus.NOT_ARCHIVE.getStatus()).message("No archive").build();
    }

    @Override
    public BaseResponse<List<PostDto>> getMyPostList() {
        BaseResponse<UserEntity> BaseUserResponse = userService.getUser();

        if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, BaseUserResponse.getMessage());
        }

        UserEntity user = BaseUserResponse.getData();

        return BaseResponse.<List<PostDto>>builder()
                .status("00")
                .message("get post list successfully")
                .data(user.getMyPostList().stream().map(postMapper::toDto).collect(Collectors.toList())).build();
    }

    @Override
    @Transactional
    public BaseResponse<Page<PostDto>> getMyArchivePostList(Integer page) {
        BaseResponse<UserEntity> BaseUserResponse = userService.getUser();

        if (BaseUserResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            throw new BaseException(ConstantUtils.BASERESPONSE_STATUS_FAILDED, BaseUserResponse.getMessage());
        }

        UserEntity user = BaseUserResponse.getData();

        Pageable pageable = PageRequest.of(page, 6);

        Page<PostEntity> postEntityPage = postUserRepository.getAllByUserIdAndIsArchive(user.getId(),ConstantUtils.POST_USER_ARCHIVE, pageable);

        return responsePostDtoList(postEntityPage,pageable);

    }

    private BaseResponse<Page<PostDto>> responsePostDtoList(Page<PostEntity> list,Pageable pageable){
        if (list.isEmpty()) {
            return BaseResponse.<Page<PostDto>>builder().status(ConstantUtils.BASERESPONSE_STATUS_FAILDED).message("Not list").build();
        }


        Page<PostDto> lst = new PageImpl<>(list.getContent()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList()), pageable, list.getTotalElements());

        return BaseResponse.<Page<PostDto>>builder().status(ConstantUtils.BASERESPONSE_STATUS_SUCCESS).message("Get post list successfully").data(lst).build();
    }


}
