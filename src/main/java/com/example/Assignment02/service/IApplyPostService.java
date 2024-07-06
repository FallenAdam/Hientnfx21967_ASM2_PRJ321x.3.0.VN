package com.example.Assignment02.service;

import com.example.Assignment02.entity.ApplyPosts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IApplyPostService {
        void approveApplication(int userId, int recruitmentId);

    ApplyPosts getApplyJobById(int id);

    List<ApplyPosts> getAllApplyPost();

    Page<ApplyPosts> findAllUser(Pageable pageable);

    String applyJob(int idRe, String text, int idUser, MultipartFile file) throws IOException;
}
