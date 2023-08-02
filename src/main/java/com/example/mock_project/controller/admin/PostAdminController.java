package com.example.mock_project.controller.admin;


import com.example.mock_project.service.PostService;
import com.example.mock_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin/post")
public class PostAdminController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String loadPostReviewPage() {

        return "admin/post";
    }




}
