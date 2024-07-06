package com.example.Assignment02.service;

import com.example.Assignment02.entity.Cv;
import com.example.Assignment02.form.UpdateUserImageForm;
import com.example.Assignment02.form.UploadFileCvForm;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public interface ICvService {
    String uploadCvUser(UploadFileCvForm form, MultipartFile file) throws IOException;


    void deleteCvById(int id);
}
