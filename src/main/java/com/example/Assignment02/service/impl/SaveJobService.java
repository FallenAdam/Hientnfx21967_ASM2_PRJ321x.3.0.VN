package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.SaveJob;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.repository.IRecruitmentRepository;
import com.example.Assignment02.repository.ISaveJobRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.ISaveJobService;
import com.example.Assignment02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaveJobService implements ISaveJobService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRecruitmentRepository recruitmentRepository;

    @Autowired
    private ISaveJobRepository saveJobRepository;

    @Autowired
    private IUserService userService;

    @Override

    public String saveJob(int idRe, int id) {
        SaveJob saveJob = saveJobRepository.findByUserIdAndRecruitmentId(id, idRe);
        if (Objects.isNull(saveJob)) {
            //tao moi khoi tao save job
            SaveJob saveJob1 = new SaveJob();
            // tim re theo id, set RE cho saveJob
            Recruitment recruitment = recruitmentRepository.findById(idRe).get();
            if (Objects.isNull(recruitment)) {
                throw new RuntimeException("Not Found");
            }
            saveJob1.setRecruitment(recruitment);
            //tim user theo id
            User user = userRepository.findById(id).get();
            if (Objects.isNull(user)) {
                throw new RuntimeException("Not Found");
            }
            saveJob1.setUser(user);
            //save saveJob
            saveJobRepository.save(saveJob1);
            return "save";
        } else {
            saveJobRepository.delete(saveJob);
            return "un-save";
        }

    }

    // Lấy ra danh sách Recruitment theo User ID trong SaveJob
    @Override
    public Page<Recruitment> getListRecruitmentByUserId(Pageable pageable, int userId) {
        List<SaveJob> saveJobs = saveJobRepository.findByUserId(userId);// tong so ptu
        Page<SaveJob> saveJobs1 = saveJobRepository.findByUserId(pageable, userId);// ds ptu theo tung trang
        List<Recruitment> recruitments = saveJobs1.getContent().stream().map(i -> i.getRecruitment()).collect(Collectors.toList());
        Page<Recruitment> pages = new PageImpl<>(recruitments, pageable, saveJobs.size());

        return pages;
    }


}
