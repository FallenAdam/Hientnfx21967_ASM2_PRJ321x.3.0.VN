package com.example.Assignment02.controller;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.*;
import com.example.Assignment02.form.CreateRecruitmentForm;
import com.example.Assignment02.form.UpdateRecruitmentForm;
import com.example.Assignment02.service.*;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class RecruitmentController {
    @Autowired
    private IApplyPostService applyPostService;
    @Autowired
    private IRecruitmentService recruitmentService;

    @Autowired
    private IFollowCompanyService followCompanyService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IUserService userService;


    @Autowired
    private ContextUtils contextUtils;

    @GetMapping(value = "list-recruitment")
    public String getAllRecruitment(ModelMap modelMap,
                                    @RequestParam(value = "page") Optional<Integer> page,
                                    @RequestParam(value = "size") Optional<Integer> size){
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<Recruitment> pages = recruitmentService.findAllRecruitment(PageRequest.of(p,s));
        modelMap.addAttribute("recruitments", pages);
        modelMap.addAttribute("pageNumber", pages.getNumber());
        return "public/list-recruitment";

    }
    // Tìm kiếm theo tên tuyển dụng
    @GetMapping(value = "/recruitment/search-title")
    public String searchByCompanyName(ModelMap modelMap,
                                      @RequestParam(value = "page") Optional<Integer> page,
                                      @RequestParam(value = "size") Optional<Integer> size,
                                      @RequestParam(value = "keyword", required = false) String search) {

        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<Recruitment> recruitments = recruitmentService.findAllRecruitmentByTitle(PageRequest.of(p,s), search);
        modelMap.addAttribute("recruitments", recruitments);
        modelMap.addAttribute("numberRecruitment", recruitments.getTotalElements());
        modelMap.addAttribute("pageNumber", recruitments.getNumber());
        modelMap.addAttribute("keyword", search);

        return "public/result-search";
    }

    // Lấy ra danh sách công việc của công ty đó
    @GetMapping(value = "/user/post-list")
    public String getAllRecruitment(ModelMap modelMap,
                                    @RequestParam("page") Optional<Integer> page) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        // lauy ra company tu barng follow  theo userid
        Company company = companyService.findByUserId(userLoginDTO.getId());
        if (Objects.isNull(company)) {
            throw new RuntimeException("Not Found CompanyID : " + userLoginDTO.getId());
        }
        int p = page.isPresent() ? page.get() : 0;
        Page<Recruitment> recruitments = recruitmentService.getPostPageByCompanyId(p, 5, company.getId());
        List<Category> categories = categoryService.getAllCategory();
        modelMap.addAttribute("company", company.getRecruitments());
        modelMap.addAttribute("recruitments", recruitments);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("pageNumber", recruitments.getNumber());
        return "public/post-list";
    }

    //
    @GetMapping("/user/post-job")
    public String postJob(ModelMap modelMap) {
        List<Recruitment> lists = recruitmentService.getAllRecruitment();
        List<Category> categories = categoryService.getAllCategory();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("recruitments", lists);
        return "public/post-job";
    }

    ;

    // Hiện thị chi tiết công việc và danh sách ứng tuyển công việc
    @GetMapping("/detail-post")
    public String getDetailPost(int id, ModelMap modelMap) {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        if (Objects.isNull(userLoginDTO)){
            modelMap.addAttribute("userlogin", "null");
        }else {
            modelMap.addAttribute("userlogin", userLoginDTO);
        }

        Recruitment recruitmen = recruitmentService.findById(id);
        modelMap.addAttribute("recruitment", recruitmen);

        List<Recruitment> recruitments = recruitmentService.getAllRecruitment();// get apply post by recr_id
        modelMap.addAttribute("listRelated", recruitments);
        return "public/detail-post";
    }


    @PostMapping(value = "/deleteRecruitment")
    public String deleteRecruitment(@RequestParam int id) {
        recruitmentService.deleteRecruitment(id);
        return "redirect:/user/post-list";
    }

    @PostMapping(value = "/updateRecruitment")
    public String updateRecruitment(@ModelAttribute UpdateRecruitmentForm form) throws ParseException {
        recruitmentService.updateRecruitment(form);
        return "redirect:/user/post-list";
    }

    @PostMapping(value = "/createNewRecruitment")
    public String createRecruitment(@ModelAttribute CreateRecruitmentForm form) throws ParseException {
        recruitmentService.createRecruitment(form);
        return "redirect:/user/post-list";
    }

    @GetMapping("/user/approve/{userId}/{recruitmentId}")
    public String approveApplication(
            @PathVariable int userId,
            @PathVariable int recruitmentId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            applyPostService.approveApplication(userId, recruitmentId);
            redirectAttributes.addFlashAttribute("message", "Application approved successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error approving application: " + e.getMessage());
        }
        return "redirect:/detail-post?id=" + recruitmentId; // Redirect to the appropriate page after approval
    }



}
