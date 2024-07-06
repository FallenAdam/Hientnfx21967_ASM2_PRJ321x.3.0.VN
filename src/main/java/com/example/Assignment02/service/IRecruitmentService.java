package com.example.Assignment02.service;

import com.example.Assignment02.dto.IRecruitmentDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.form.CreateRecruitmentForm;
import com.example.Assignment02.form.UpdateRecruitmentForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IRecruitmentService {
    public List<Recruitment> getAllRecruitment();

    IRecruitmentDTO getRecruitmentInHome();

    Recruitment findById(int id);

    Page<Recruitment> findAllRecruitment( Pageable pageable);
    Page<Recruitment> findAllRecruitmentByCompanyAddress( Pageable pageable, String key);

    Page<Recruitment> findAllRecruitmentByCompanyName(Pageable pageable, String key);

    // Tìm kiếm theo tên của bài tuyển dụng
    Page<Recruitment> findAllRecruitmentByTitle(Pageable pageable, String key);

    void deleteRecruitment(int id);



    void updateRecruitment(UpdateRecruitmentForm form) throws ParseException;

    void createRecruitment(CreateRecruitmentForm form) throws ParseException;

    Page<Recruitment> getPostPageByCompanyId(int page, int size, int id);

}
