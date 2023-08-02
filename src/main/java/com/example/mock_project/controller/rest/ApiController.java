package com.example.mock_project.controller.rest;


import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.ConditionPost;
import com.example.mock_project.dto.FollowerDto;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.service.FollowService;
import com.example.mock_project.service.PostService;
import com.example.mock_project.util.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@RestController
@Slf4j
public class ApiController {
    @Autowired
    private PostService postService;
    @Autowired
    private FollowService followService;

    @PostMapping(value = "/api/create-post")
    public ResponseEntity<BaseResponse<PostDto>> createPost(@RequestPart("post") PostDto postDto,
                                                            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {
        return ResponseEntity.ok(postService.savePost(postDto, multipartFile));
    }
    @PostMapping(value = "/api/edit-post")
    public ResponseEntity<BaseResponse<PostDto>> editPost(@RequestPart("post") PostDto postDto,
                                                            @RequestPart(value = "file", required = false) MultipartFile multipartFile) {


        return ResponseEntity.ok(postService.savePost(postDto, multipartFile));
    }
    @PostMapping(value = "/api/search-post-by-condition")
    public ResponseEntity<BaseResponse<Page<PostDto>>> searchPostByCondition(@RequestBody ConditionPost conditionPost,@RequestParam("page") Integer page) {
        return ResponseEntity.ok(postService.getPostListByCondition(conditionPost,page));
    }

    @PostMapping(value = "/api/search-post-profile")
    public ResponseEntity<BaseResponse<Page<PostDto>>> searchPostProfile(@RequestBody ConditionPost conditionPost,@RequestParam("page") Integer page) {
        return ResponseEntity.ok(postService.getPostListProfile(conditionPost,page));
    }
    @PostMapping(value = "/api/search-post-archive")
    public ResponseEntity<BaseResponse<Page<PostDto>>> searchPostArchive(@RequestBody ConditionPost conditionPost,@RequestParam("page") Integer page) {
        return ResponseEntity.ok(postService.getMyArchivePostList(page));
    }

    @PostMapping(value = "/api/search-post-by-condition-time")
    public ResponseEntity<BaseResponse<Page<PostDto>>> searchPostByConditionTime(@RequestBody ConditionPost conditionPost,@RequestParam("page") Integer page) {
        return ResponseEntity.ok(postService.getPostListByConditionTime(conditionPost,page));
    }



    @PostMapping( "/api/follow-user")
    public ResponseEntity<BaseResponse<Void>> followUser(@RequestBody Long idFollowee) {
        return ResponseEntity.ok(followService.changeFollow(idFollowee));
    }

    @PostMapping( "/api/save-archive-post")
    public ResponseEntity<BaseResponse<Void>> saveArchivePost(@RequestBody Long postId) {
        return ResponseEntity.ok(postService.saveArchivePost(postId));
    }
}
