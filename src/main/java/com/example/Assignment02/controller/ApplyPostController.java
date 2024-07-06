package com.example.Assignment02.controller;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.ApplyPosts;
import com.example.Assignment02.service.IApplyPostService;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@Controller
public class ApplyPostController {
    @Autowired
    private IApplyPostService applyPostService;


    @Autowired
    private ContextUtils contextUtils;

    @GetMapping("/list-user")
    public String getAllUser(ModelMap modelMap,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        int p = page.isPresent() ? page.get() : 0;
        int s = size.isPresent() ? size.get() : 5;
        Page<ApplyPosts> pages = applyPostService.findAllUser(PageRequest.of(p, s));
        modelMap.addAttribute("list", pages);
        modelMap.addAttribute("pageNumber", pages.getNumber());
        return "public/list-user";
    }


    @PostMapping("/user/apply-job1")
    @ResponseBody
    public String applyJob1( @RequestParam(name = "idRe") int idRe, @RequestParam(name = "text") String text, MultipartFile file) throws IOException {
        try {
            UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
          return applyPostService.applyJob(idRe, text, userLoginDTO.getId(), file);
        }catch (Exception e){
            return "not-login";
        }
    }

    @PostMapping("/user/apply-job")
    @ResponseBody
    public String applyJob(@RequestParam(name = "idRe") int idRe, @RequestParam(name = "text") String text, MultipartFile file) throws IOException {
        try{
            UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
       return applyPostService.applyJob(idRe, text, userLoginDTO.getId(), file);

    }catch (Exception e){
            return "not-login";
        }
    }


}
