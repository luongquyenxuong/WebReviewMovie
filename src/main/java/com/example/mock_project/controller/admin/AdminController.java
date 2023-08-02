package com.example.mock_project.controller.admin;


import com.example.mock_project.service.PostService;
import com.example.mock_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @GetMapping("/dashboard")
    public String loadHomePage(Model model, HttpServletRequest request) {
        HttpSession session= request.getSession();

        model.addAttribute("fullName",session.getAttribute("fullName"));

        return "admin/index";
    }




}
