package com.example.mock_project.controller.user;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.service.FollowService;
import com.example.mock_project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    FollowService followService;

    @PostMapping("/detail-post")
    public String detailPost(Model model, HttpServletRequest request) {
        //Lay chi tiet post
        BaseResponse<PostDto> baseResponsePost = postService.findPost(Long.parseLong(request.getParameter("postId")));

        if (baseResponsePost.getStatus().equals("99")) {
            model.addAttribute("error", baseResponsePost.getMessage());
        }
        //Kiem tra da follow chua
        BaseResponse<Void> responseFollow = followService.isFollowed(baseResponsePost.getData().getUserId());

        BaseResponse<Void> responseArchivePost=postService.isArchivePost(Long.parseLong(request.getParameter("postId")));

        model.addAttribute("post", baseResponsePost.getData());
        model.addAttribute("isLogin", responseFollow.getStatus()=="04"?false:true);
        model.addAttribute("followStatus", responseFollow.getStatus());
        model.addAttribute("archiveStatus", responseArchivePost.getStatus());


        return "user/detail-post";
    }
    @GetMapping("/create")
    public String loadCreatePage() {
        return "user/create-post";
    }

    @PostMapping("/edit-post")
    public String loadCreatePage(Model model,HttpServletRequest request) {
        BaseResponse<PostDto> baseResponsePost = postService.findPost(Long.parseLong(request.getParameter("postId")));
        model.addAttribute("post",baseResponsePost.getData());
        return "user/edit-post";
    }

}
