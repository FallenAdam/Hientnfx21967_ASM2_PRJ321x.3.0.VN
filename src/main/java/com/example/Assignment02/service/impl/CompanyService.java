package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.ICompanyDTO;
import com.example.Assignment02.dto.UserLoginDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.SaveJob;
import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.UpdateCompanyImageForm;
import com.example.Assignment02.form.UpdateCompanyProfileForm;
import com.example.Assignment02.repository.ICompanyRepository;
import com.example.Assignment02.repository.IFollowCompanyRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.ICompanyService;
import com.example.Assignment02.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyService implements ICompanyService {
    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IFollowCompanyRepository followCompanyRepository;


    public final  String UPLOAD_DIRECTORY_COMPANY = System.getProperty("user.dir") + "/src/main/resources/static/assets/images/company-logo/";

    @Override
    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }


    @Override
    public ICompanyDTO getCompanieInHome() {
        return companyRepository.getCompanieInHome();
    }



    @Override
    public Company findById(int id) {
        Optional<Company> optional = companyRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Not Found ID :" + id);
        }
        return optional.get();
    }



    @Override
    public void updateCompanyProfile(UpdateCompanyProfileForm form) {
        if (Objects.isNull(form.getIdCompany())) {

            Company company1 = new Company();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal().toString().equals("anonymousUser")) {
            } else {
                org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                UserLoginDTO userLoginDTO = (UserLoginDTO) userService.loadUserByUsername(userLogin.getUsername());
                User user = userRepository.findById(userLoginDTO.getId()).get();

                company1.setEmail(form.getEmail());
                company1.setUser(user);
                company1.setNameCompany(form.getNameCompany());
                company1.setAddress(form.getAddress());
                company1.setPhoneNumber(form.getPhoneNumber());
                company1.setDescription(form.getDescription());
                companyRepository.save(company1);
                }
            }else{
                Optional<Company> optional = companyRepository.findById(form.getIdCompany());
                if (optional.isPresent()) {
                    Company company = optional.get();
                    company.setEmail(form.getEmail());
                    company.setNameCompany(form.getNameCompany());
                    company.setAddress(form.getAddress());
                    company.setPhoneNumber(form.getPhoneNumber());
                    company.setDescription(form.getDescription());
                    companyRepository.save(company);

                }
            }
        }



    @Override
    public String updateCompanyImage(UpdateCompanyImageForm form, MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().equals("anonymousUser"))
            throw new RuntimeException("not found");

        org.springframework.security.core.userdetails.User userLogin = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        UserLoginDTO userLoginDTO = (UserLoginDTO) userService.loadUserByUsername(userLogin.getUsername());

        Optional<Company> optional= Optional.ofNullable(companyRepository.findByUserId(userLoginDTO.getId()));
        if (!optional.isPresent())
            throw new RuntimeException("Not Found ID :" +form.getId());
        Company company = optional.get();

        // Thêm anh vào thư muc /assets/images/ và database
        String[] arr = file.getOriginalFilename().split("\\.");
        String tailImg = ""; // Lấy đuôi của ảnh (.pjg)
        if (arr.length>0)
            tailImg = arr[arr.length-1];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");//abc.jpg
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY_COMPANY, simpleDateFormat.format(new Date()) + company.getNameCompany() + "."+ tailImg);
        Files.write(fileNameAndPath, file.getBytes());//  ghi file vao thu muc tuong ung

        //Lưu ảnh vào Company
        company.setLogo(simpleDateFormat.format(new Date()) + company.getNameCompany() + "."+ tailImg);
        companyRepository.save(company);

        return company.getLogo();
    }


    // Lấy ra danh sách Company theo User ID trong FollowCompany



    @Override
    public Company findByUserId(int id) {
        Optional<Company> optional = Optional.ofNullable(companyRepository.findByUserId(id));
        if (!optional.isPresent()) {
            throw new RuntimeException("Not Found ID :" + id);
        }
        return optional.get();
    }


}
