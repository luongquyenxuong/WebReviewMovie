package com.example.mock_project.controller.authencation;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.PostDto;
import com.example.mock_project.entity.UserEntity;
import com.example.mock_project.service.UserService;
import com.example.mock_project.util.CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Value("${jwt.duration}")
    public Integer duration;


    @GetMapping("/login")
    public String loadLoginPage(Model model) {
        model.addAttribute("error", BaseResponse.buildEmpty());
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {

        CookieHelper.removeCookie(httpServletResponse,"jwt_token");

        return "redirect:/login";
    }
    @PostMapping("/login")
    public String login(Model model,
                        @RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse httpServletResponse,HttpServletRequest request) throws IOException {

        BaseResponse<List<String>> response = userService.login(username, password);

        if (response.getStatus().equals("99")) {
            model.addAttribute("error", response);
            return "login";
        }

        CookieHelper.setCookie(
                httpServletResponse,
                "jwt_token",
                response.getData().get(0),
                duration
        );

        HttpSession session=request.getSession();
        session.setAttribute("fullName",response.getData().get(2));



        if (response.getData().get(1).equals("USER")) {
            return "redirect:/user/home";
        }
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/register")
    public String loadRegisterPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserEntity user, Model model) {
        // call service save to db
        BaseResponse<Void> baseResponse = userService.saveUser(user);
        if (baseResponse.getStatus().equals("99")) {
            model.addAttribute("user", new UserEntity());
            model.addAttribute("error", baseResponse.getMessage());
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/access-denied")
    public String navigateAccessDeny() {
        return "deny-page";
    }


}
