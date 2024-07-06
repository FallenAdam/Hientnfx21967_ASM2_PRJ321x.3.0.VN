package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.IRecruitmentDTO;
import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.*;
import com.example.Assignment02.form.CreateRecruitmentForm;
import com.example.Assignment02.form.UpdateRecruitmentForm;
import com.example.Assignment02.repository.*;
import com.example.Assignment02.service.IRecruitmentService;
import com.example.Assignment02.service.IUserService;
import com.example.Assignment02.specification.recruitment.RecruitmentSpecification;
import com.example.Assignment02.utils.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RecruitmentService implements IRecruitmentService {
    @Autowired
    private IRecruitmentRepository recruitmentRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFollowCompanyRepository followCompanyRepository;

    @Autowired
    private ISaveJobRepository saveJobRepository;

    @Autowired
    private ContextUtils contextUtils;

    @Override
    public List<Recruitment> getAllRecruitment() {
        return recruitmentRepository.findAll();
    }

    @Override
    public IRecruitmentDTO getRecruitmentInHome() {
        return recruitmentRepository.getRecruitmentInHome();
    }

    @Override
    public Recruitment findById(int id) {
        Optional<Recruitment> optional = recruitmentRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Not Found ID");
        }
        return optional.get();

    }

    @Override
    public Page<Recruitment> findAllRecruitment(Pageable pageable) {
        Specification<Recruitment> where = null;
        RecruitmentSpecification status = new RecruitmentSpecification("status", 0);
        where = Specification.where(status);
       return recruitmentRepository.findAll(where,pageable);
    }

    // Tìm kiếm theo địa chỉ của công ty
    @Override
    public Page<Recruitment> findAllRecruitmentByCompanyAddress(Pageable pageable, String key) {
        Specification<Recruitment> where = null;
        RecruitmentSpecification status = new RecruitmentSpecification("status", 0);
        where = Specification.where(status);
        if (!StringUtils.isEmpty(key)) {
            RecruitmentSpecification adress = new RecruitmentSpecification("address", key);
            where = where.and(adress);
        }
            Page<Recruitment> recruitments = recruitmentRepository.findAll(where, pageable);

        return recruitments;
    }
    // Tìm kiếm theo tên của công ty
    @Override
    public Page<Recruitment> findAllRecruitmentByCompanyName(Pageable pageable, String key) {
        Specification<Recruitment> where = null;
        RecruitmentSpecification status = new RecruitmentSpecification("status", 0);
        where = Specification.where(status);
        if (!StringUtils.isEmpty(key)) {
            RecruitmentSpecification nameCompany = new RecruitmentSpecification("nameCompany", key);
            where = where.and(nameCompany);
        }
        Page<Recruitment> recruitments = recruitmentRepository.findAll(where, pageable);

        return recruitments;
    }

    // Tìm kiếm theo tên của bài tuyển dụng
    @Override
    public Page<Recruitment> findAllRecruitmentByTitle(Pageable pageable, String key) {
        Specification<Recruitment> where = null;
        RecruitmentSpecification status = new RecruitmentSpecification("status", 0);
        where = Specification.where(status);
        if (!StringUtils.isEmpty(key)) {
            RecruitmentSpecification nameCompany = new RecruitmentSpecification("title", key);
            where = where.and(nameCompany);
        }
        Page<Recruitment> recruitments = recruitmentRepository.findAll(where, pageable);

        return recruitments;
    }

    @Override
    public void deleteRecruitment(int id) {
        recruitmentRepository.deleteById(id);
    }




    @Override
    public void updateRecruitment(UpdateRecruitmentForm form) throws ParseException {
        Optional<Recruitment> optional = recruitmentRepository.findById(form.getId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Recruitment recruitment = null;
        if (optional.isPresent()) {
            recruitment = optional.get();

            // Set các giá trị cho recruitment
            recruitment.setTitle(form.getTitle());
            recruitment.setDescription(form.getDescription());
            recruitment.setExperience(form.getExperience());
            recruitment.setQuantity(form.getQuantity());
            recruitment.setAddress(form.getAddress());
            recruitment.setDeadline(String.valueOf(simpleDateFormat.parse(form.getDeadline())));
            recruitment.setSalary(form.getSalary());
            recruitment.setType(form.getType());
            Optional<Category> optionalCategory = categoryRepository.findById(form.getCategory_id());
            if (!optionalCategory.isPresent()){
                throw new RuntimeException("Not Found ID");
            }
            recruitment.setCategory(optionalCategory.get());
            // Lưu giá trị
            recruitmentRepository.save(recruitment);
        } else {
            throw new RuntimeException("Không tìm thấy Recruitment có ID là :" + form.getId());
        }
    }

    @Override
    public void createRecruitment(CreateRecruitmentForm form) throws ParseException {
        Recruitment recruitment;
        recruitment = addNewRecruitment(form);
        recruitmentRepository.save(recruitment);
    }

    @Override
    public Page<Recruitment> getPostPageByCompanyId(int page, int size, int companyId) {
        Page<Recruitment> pages = recruitmentRepository.findByCompanyId(PageRequest.of(page, size), companyId);
        return pages;
    }



    public Recruitment addNewRecruitment(CreateRecruitmentForm form) throws ParseException {

        Recruitment recruitment = new Recruitment();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        recruitment.setTitle(form.getTitle());
        recruitment.setDescription(form.getDescription());
        recruitment.setExperience(form.getExperience()+ "Year");
        recruitment.setQuantity(form.getQuantity());
        recruitment.setAddress(form.getAddress());
        recruitment.setDeadline(String.valueOf(simpleDateFormat.parse(form.getDeadline())));
        recruitment.setSalary(form.getSalary());
        recruitment.setType(form.getType());
        // lay dc cai userid tu cai login vao

        UserLoginDTO userLoginDTO = (UserLoginDTO) contextUtils.getUserLogin();
        // lauy ra company tu barng follow  theo userid
        Company company = companyRepository.findByUserId(userLoginDTO.getId());
        if (Objects.isNull(company)){
            throw new RuntimeException("Not Found CompanyID : " + userLoginDTO.getId());
        }
        recruitment.setCompany(company);
        recruitment.setCreatedAt(String.valueOf(LocalDate.now()));
        Optional<Category> optionalCategory = categoryRepository.findById(form.getCategory_id());
        if (!optionalCategory.isPresent()){
            throw new RuntimeException("Not Found ID :" + form.getCategory_id());
        }
        recruitment.setCategory(optionalCategory.get());
        return recruitment;
    }








}
