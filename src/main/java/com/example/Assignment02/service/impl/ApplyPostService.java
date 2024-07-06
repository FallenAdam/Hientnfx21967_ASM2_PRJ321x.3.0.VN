package com.example.Assignment02.service.impl;

import com.example.Assignment02.entity.ApplyPosts;
import com.example.Assignment02.entity.Cv;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.repository.IApplyPostsRepository;
import com.example.Assignment02.repository.ICvRepository;
import com.example.Assignment02.repository.IRecruitmentRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.IApplyPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ApplyPostService implements IApplyPostService {

    @Autowired
    private IApplyPostsRepository applyPostsRepository;

    @Autowired
    private IRecruitmentRepository recruitmentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICvRepository cvRepository;

    public final String UPLOAD_DIRECTORY_CV = Paths.get("src", "main", "resources", "static", "assets", "upload_files").toString();

    @Override
    public List<ApplyPosts> getAllApplyPost() {
        return applyPostsRepository.findAll();
    }

    @Override
    public void approveApplication(int userId, int recruitmentId) {
        // Find the apply post by userId and recruitmentId
        Optional<ApplyPosts> applyPostOpt = Optional.ofNullable(applyPostsRepository.findByUserIdAndRecruitmentId(userId, recruitmentId));
        if (applyPostOpt.isPresent()) {
            ApplyPosts applyPost = applyPostOpt.get();
            // Update the status to approved
            applyPost.setStatus(1);
            applyPostsRepository.save(applyPost);
        } else {
            throw new RuntimeException("Application not found for userId: " + userId + " and recruitmentId: " + recruitmentId);
        }
    }

    @Override
    public Page<ApplyPosts> findAllUser(Pageable pageable) {
        return applyPostsRepository.findAll(pageable);
    }
    @Override
    public String applyJob(int idRe, String text, int idUser, MultipartFile file) throws IOException {
        ApplyPosts applyPosts = new ApplyPosts();

        // Set value for Recruitment
        Optional<Recruitment> optionalRecruitment = recruitmentRepository.findById(idRe);
        if (!optionalRecruitment.isPresent()) {
            throw new RuntimeException("Recruitment not found");
        }
        applyPosts.setRecruitment(optionalRecruitment.get());
        applyPosts.setText(text);

        // Set value for User
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        applyPosts.setUser(optionalUser.get());
        applyPosts.setStatus(0);

        // Check if the user has already applied
        ApplyPosts existingApplyPost = applyPostsRepository.findByUserIdAndRecruitmentId(idUser, idRe);
        if (existingApplyPost != null) {
            return "false";
        }

        // Upload CV file if provided
        if (file != null && !file.isEmpty()) {
            String fileName = generateUniqueFileName(file, optionalUser.get().getFullName());
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_CV, fileName);
            Files.write(fileNameAndPath, file.getBytes());
            applyPosts.setNameCv(fileName);
        } else {
            // If no file provided, use the user's existing CV
            Cv cv = cvRepository.findByUserId(idUser);
            if (cv != null) {
                applyPosts.setNameCv(cv.getFileName());
            }
        }

        applyPosts.setCreatedAt(String.valueOf(LocalDate.now()));
        applyPostsRepository.save(applyPosts);
        return "true";
    }

    private String generateUniqueFileName(MultipartFile file, String userFullName) {
        String[] arr = file.getOriginalFilename().split("\\.");
        String fileExtension = arr.length > 1 ? arr[arr.length - 1] : "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return userFullName.replaceAll("[^a-zA-Z0-9]", "") + "_" + timestamp + "." + fileExtension;
    }

    @Override
    public ApplyPosts getApplyJobById(int id) {
        Optional<ApplyPosts> optional = applyPostsRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Not Found ID :" + id);
        }
        return optional.get();
    }
}
