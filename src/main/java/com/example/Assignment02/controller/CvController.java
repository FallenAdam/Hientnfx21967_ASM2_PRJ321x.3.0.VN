package com.example.Assignment02.controller;

import com.example.Assignment02.form.Updateform;
import com.example.Assignment02.form.UploadFileCvForm;
import com.example.Assignment02.service.ICvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
public class CvController {

    @Autowired
    private ICvService cvService;

    @PostMapping(value = "/user/uploadCv")
    @ResponseBody
    public String uploadFileUser(@ModelAttribute UploadFileCvForm form, @RequestParam MultipartFile file) throws IOException {
        return cvService.uploadCvUser(form, file);
    }

    @PostMapping(value = "/user/deleteCv")
    public String deleteCv(@RequestParam(name = "idCv")  int id){
       cvService.deleteCvById(id);
        return "redirect:/profile";
    }


}
