package com.example.mock_project.controller.admin;

import com.example.mock_project.dto.BaseResponse;
import com.example.mock_project.dto.UserDto;
import com.example.mock_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserAdminController {

    @Autowired
    UserService userService;

    @GetMapping("/user")
    public String loadUserPage(Model model) {
        List<UserDto> listUserDto = userService.getAll();
        model.addAttribute("users", listUserDto);
        return "admin/user";
    }

    @PostMapping("/user/delete")
    public String deleteUser(Model model, HttpServletRequest request ) {
        BaseResponse<Void> baseResponse= userService.deleted(Long.parseLong(request.getParameter("userId")));
        if(baseResponse.getStatus().equals("99")){
            model.addAttribute("error", baseResponse.getMessage());
        }
        return "redirect:/admin/user";
    }

}
