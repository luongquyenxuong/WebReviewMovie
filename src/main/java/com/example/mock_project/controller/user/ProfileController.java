package com.example.mock_project.controller.user;

import com.example.mock_project.constant.ConstantUtils;
import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.service.PostService;
import com.example.mock_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    PostService postService;


    @GetMapping("/home")
    public String loadProfilePage(Model model) {

        model.addAttribute("myPostList", postService.getMyPostList().getData());

        return "user/profile";
    }

    @GetMapping("/archive-page")
    public String loadArchivePostPage(Model model) {

//        BaseResponse<Page<PostDto>> response = postService.getMyArchivePostList(0);
//
//        if (response.getStatus().equals(ConstantUtils.BASERESPONSE_STATUS_FAILDED)) {
//            model.addAttribute("isHaveList",false);
//        } else {
//            model.addAttribute("isHaveList",true);
//            model.addAttribute("myArchivePostList", response.getData());
//
//        }


        return "user/archive-page";
    }

}
