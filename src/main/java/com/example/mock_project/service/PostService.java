package com.example.mock_project.service;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.ConditionPost;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

public interface PostService {
    BaseResponse<PostDto> savePost(PostDto post, MultipartFile multipartFile);

    BaseResponse<PostDto> findPost(Long postId);

    BaseResponse<Page<PostDto>> getPostListByCondition(ConditionPost conditionPost,Integer page);

    BaseResponse<Page<PostDto>> getPostListByConditionTime(ConditionPost conditionPost,Integer page);

    BaseResponse<Page<PostDto>> getPostListProfile(ConditionPost conditionPost,Integer page);

    BaseResponse<Page<PostDto>> getPostListArchive(ConditionPost conditionPost,Integer page);


    BaseResponse<Void> deleted(Long id);

    BaseResponse<Void> updateStatusPost(Long idPost,Integer status);

    BaseResponse<Void> saveArchivePost(Long postId);

    BaseResponse<Void> isArchivePost(Long postId);

    BaseResponse<List<PostDto>> getMyPostList();

    BaseResponse<Page<PostDto>> getMyArchivePostList(Integer page);

}
