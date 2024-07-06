package com.example.Assignment02.controller;

import com.example.Assignment02.dto.ICategoryDTO;
import com.example.Assignment02.dto.ICompanyDTO;
import com.example.Assignment02.dto.IRecruitmentDTO;
import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.*;
import com.example.Assignment02.service.*;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
public class HomePageController {
    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IRecruitmentService recruitmentService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ContextUtils contextUtils;

    @GetMapping("/login")
    public String loginPage(){
        return "public/login";
    };

    @GetMapping("/register")
    public String registerPage() {
        return "public/register";
    };

    @GetMapping("/home")
    public String homePages( ModelMap modelMap) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        List<Company> companies = companyService.findAllCompany();
        modelMap.addAttribute("companies", companies);
        modelMap.addAttribute("numberCompany", companies.size());

        List<Recruitment> recruitments = recruitmentService.getAllRecruitment();
        modelMap.addAttribute("recruitments", recruitments);
        modelMap.addAttribute("numberRecruitment", recruitments.size());

        List<Category> categories = categoryService.getAllCategory();
        modelMap.addAttribute("categories", categories);

        List<User> users = userService.getAllUser();
        modelMap.addAttribute("users", users);
        modelMap.addAttribute("numberUser", users.size());

        ICompanyDTO companyDTO = companyService.getCompanieInHome();
        modelMap.addAttribute("company", companyDTO);

        IRecruitmentDTO recruitmentDTO =  recruitmentService.getRecruitmentInHome();
        modelMap.addAttribute("recruitment", recruitmentDTO);

        List<ICategoryDTO> categoryDTOList = categoryService.getCategoryInHome();
        modelMap.addAttribute("categories", categoryDTOList);

        return "/public/home";
    }
}
