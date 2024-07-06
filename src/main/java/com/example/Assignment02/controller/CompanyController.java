package com.example.Assignment02.controller;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.UpdateCompanyImageForm;
import com.example.Assignment02.form.UpdateCompanyProfileForm;
import com.example.Assignment02.service.ICompanyService;
import com.example.Assignment02.service.IFollowCompanyService;
import com.example.Assignment02.service.IRecruitmentService;
import com.example.Assignment02.service.IUserService;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IRecruitmentService recruitmentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFollowCompanyService followCompanyService;


    @Autowired
    private ContextUtils contextUtils;
    // In ra chi tiết của công ty


    @GetMapping("/user/detail-company")
    public String getCompanyById(@RequestParam int id, ModelMap modelMap) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        Company company = companyService.findById(id);
        modelMap.addAttribute("company", company);

        Recruitment recruitment = recruitmentService.findById(id);
        modelMap.addAttribute("recruitment", recruitment);
        return "/public/detail-company";
    }


    // Lấy ra danh sách công việc của công ty đó
    @GetMapping(value = "/user/company-post")
    public String getAllRecruitment(ModelMap modelMap,
                                    @RequestParam("page") Optional<Integer> page,
                                    @RequestParam("size") Optional<Integer> size) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        // lauy ra company tu barng follow  theo userid
        FollowCompany followCompany = followCompanyService.findByUserId(userLoginDTO.getId());
        if (Objects.isNull(followCompany)) {
            throw new RuntimeException("Not Found CompanyID : " + userLoginDTO.getId());
        }
        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() :5;
        Page<Recruitment> recruitments = recruitmentService.getPostPageByCompanyId(p, s, followCompany.getCompany().getId());
        modelMap.addAttribute("company", followCompany.getCompany());//1
        modelMap.addAttribute("recruitments", recruitments);//1
        modelMap.addAttribute("pageNumber", recruitments.getNumber());
        return "public/post-company";
    }

    @PostMapping(value = "/user/update-company")
    public String companyInfomation(@ModelAttribute UpdateCompanyProfileForm form) {
        companyService.updateCompanyProfile(form);
        return "redirect:/profile";
    }

    @PostMapping(value = "/user/upload-company")
    @ResponseBody
    public String uploadImgCompany(@ModelAttribute UpdateCompanyImageForm form, @RequestParam MultipartFile file) throws IOException {
     return  companyService.updateCompanyImage(form, file);

    }

    // Search theo địa chỉ của Công ty
    @GetMapping(value = "/company/search-address")
    public String searchByCompanyAddress(ModelMap modelMap,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam(value = "size") Optional<Integer> size,
                                  @RequestParam(value = "keyword", required = false) String search) {

        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<Recruitment> recruitments = recruitmentService.findAllRecruitmentByCompanyAddress(PageRequest.of(p,s), search);
        List<User> users = userService.getAllUser();
        List<Company> companies = companyService.findAllCompany();
        modelMap.addAttribute("numberUser", users.size());
        modelMap.addAttribute("recruitments", recruitments);
        modelMap.addAttribute("numberRecruitment", recruitments.getTotalElements());
        modelMap.addAttribute("numberCompany", companies.size());
        modelMap.addAttribute("pageNumber", recruitments.getNumber());
        modelMap.addAttribute("keyword", search);

        return "public/result-search";
    }

    // Search theo tên của Công ty
    @GetMapping(value = "/company/search-companyName")
    public String searchByCompanyName(ModelMap modelMap,
                                         @RequestParam("page") Optional<Integer> page,
                                         @RequestParam(value = "size") Optional<Integer> size,
                                         @RequestParam(value = "keyword", required = false) String search) {

        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<Recruitment> recruitments = recruitmentService.findAllRecruitmentByCompanyName(PageRequest.of(p,s), search);;
        modelMap.addAttribute("recruitments", recruitments);
        modelMap.addAttribute("numberRecruitment", recruitments.getTotalElements());
        modelMap.addAttribute("pageNumber", recruitments.getNumber());
        modelMap.addAttribute("keyword", search);

        return "public/result-search";
    }


}
