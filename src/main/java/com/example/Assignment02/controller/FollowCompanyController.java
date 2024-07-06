package com.example.Assignment02.controller;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.SaveJob;
import com.example.Assignment02.service.IFollowCompanyService;
import com.example.Assignment02.service.IUserService;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Optional;

@Controller
public class FollowCompanyController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IFollowCompanyService followCompanyService;

    @Autowired
    private ContextUtils contextUtils;


    @GetMapping(value = "/list-follow-company")
    public String listSavJob(ModelMap modelMap,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size")Optional<Integer> size){
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<FollowCompany> pages = followCompanyService.getListCompanyByUserId(PageRequest.of(p,s), userLoginDTO.getId() );
        modelMap.addAttribute("followCompanys",pages);
        modelMap.addAttribute("pageNumber", pages.getNumber());
        return "public/list-follow-company";
    }

    @PostMapping(value = "/user/follow-company")
    @ResponseBody
    public  String followCompany(@RequestParam(name = "idCompany") int idCompany){
     try{
       UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        return followCompanyService.saveCompany(idCompany, userLoginDTO.getId());
     }catch (Exception e){
            return "not-login";
        }
    }

}
