package com.example.Assignment02.controller;

import com.example.Assignment02.dto.ProfileDTO;
import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.Role;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.*;
import com.example.Assignment02.service.*;
import com.example.Assignment02.service.impl.FollowCompanyService;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ContextUtils contextUtils;


    @PostMapping(value = "/user/register")
    public String createUser(@ModelAttribute CreateUserForm form) {
        userService.createUser(form);
        return "redirect:/login";
    }

    @GetMapping(value = "/profile")
    public String createUser(ModelMap modelMap) {
        // goi toi service de lay ra thong tin user(id luc dang nhap) + company (follo...)

        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        } else{
            modelMap.addAttribute("userlogin", userLoginDTO);
            ProfileDTO profileDTO = userService.getProfile(userLoginDTO.getId());
            modelMap.addAttribute("userInformation", profileDTO.getUser());
            modelMap.addAttribute("companyInformation", profileDTO.getCompany());
            modelMap.addAttribute("Cv", profileDTO.getUser().getCv());
        }
        return "/public/profile";
    }

    @PostMapping(value = "/user/update-profile")
    public String updateUser(@ModelAttribute UpdateUserProfileForm form) throws IOException {
        userService.updateUserProfile(form);
        return "redirect:/profile";
    }

    @PostMapping(value = "/user/upload")
    @ResponseBody
    public String uploadImgUser(@ModelAttribute UpdateUserImageForm form, @RequestParam MultipartFile file) throws IOException {
        return userService.updateUserImage(form, file);
    }

    @PostMapping(value = "/user/confirm-account")
    public String updateUserStatus() {
        userService.updateUserStatus();
        return "redirect:/profile";
    }


}
