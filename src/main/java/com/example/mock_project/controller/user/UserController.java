package com.example.mock_project.controller.user;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.service.PostService;
import com.example.mock_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        BaseResponse<UserEntity> userEntityBaseResponse= userService.getUser();
        model.addAttribute("isLogin",true);
        if (userEntityBaseResponse.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
            model.addAttribute("isLogin",false);
        }

        return "user/index";
    }



}
