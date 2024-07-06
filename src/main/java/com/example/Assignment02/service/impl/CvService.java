package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Cv;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.UploadFileCvForm;
import com.example.Assignment02.repository.ICvRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.ICvService;
import com.example.Assignment02.service.IUserService;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CvService implements ICvService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICvRepository cvRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ContextUtils contextUtils;

    public final String UPLOAD_DIRECTORY_CV = Paths.get("src", "main", "resources", "static", "assets", "upload_files").toString();

    @Override
    public String uploadCvUser(UploadFileCvForm form, MultipartFile file) throws IOException {
        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        User user = userRepository.findById(userLoginDTO.getId()).get();

        Optional<Cv> optional = Optional.ofNullable(cvRepository.findByUserId(userLoginDTO.getId()));
        Cv cv;
        if (!optional.isPresent()) {
            cv = new Cv();
            cv.setUserId(user.getId());
        } else {
            cv = optional.get();
        }

        String fileName = createFileName(file, user.getFullName());
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_CV, fileName);
        Files.write(fileNameAndPath, file.getBytes());

        cv.setFileName(fileName);
        cvRepository.save(cv);

        user.setCv(cv);
        userRepository.save(user);
        return cv.getFileName();
    }

    private String createFileName(MultipartFile file, String fullName) {
        String[] arr = file.getOriginalFilename().split("\\.");
        String fileExtension = arr.length > 1 ? arr[arr.length - 1] : "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return fullName.replaceAll("[^a-zA-Z0-9]", "") + "_" + timestamp + "." + fileExtension;
    }

    @Override
    public void deleteCvById(int id) {
        User user = userRepository.findByCvId(id);
        user.setCv(null);
        userRepository.save(user);
        cvRepository.deleteById(id);
    }
}
