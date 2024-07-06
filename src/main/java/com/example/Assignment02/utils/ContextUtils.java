package com.example.Assignment02.utils;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.service.ICompanyService;
import com.example.Assignment02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class ContextUtils {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICompanyService companyService;

    public  UserDetails getUserLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().equals("anonymousUser"))
           return null;
        else {
            org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            UserDetails userLoginDTO = userService.loadUserByUsername(userLogin.getUsername());
            return userLoginDTO;
        }
    }
}
