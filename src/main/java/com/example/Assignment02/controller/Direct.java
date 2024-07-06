package com.example.Assignment02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Direct {
    @RequestMapping("/")
    public String home() {
        return "/home";
    }
}
