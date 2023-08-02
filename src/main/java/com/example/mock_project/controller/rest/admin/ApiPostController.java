package com.example.mock_project.controller.rest.admin;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.service.PostService;
import com.example.mock_project.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api/post")
public class ApiPostController {
    @Autowired
    private PostService postService;




    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPost(@RequestParam("post-id") Long postId) {
        BaseResponse<Void> baseResponse = postService.updateStatusPost(postId, ConstantUtils.POST_STATUS_CONFIRM);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/unconfirm")
    public ResponseEntity<?> unConfirmPost(@RequestParam("post-id") Long postId) {

        BaseResponse<Void> baseResponse = postService.updateStatusPost(postId, ConstantUtils.POST_STATUS_UNCONFIRM);
        return ResponseEntity.ok(baseResponse);
    }
    @PostMapping("/remove")
    public ResponseEntity<?> removePost(@RequestParam("post-id") Long postId) {
        BaseResponse<Void> baseResponse = postService.deleted(postId);
        return ResponseEntity.ok(baseResponse);
    }
}
