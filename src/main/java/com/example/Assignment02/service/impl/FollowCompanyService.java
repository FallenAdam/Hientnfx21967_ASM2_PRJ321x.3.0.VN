package com.example.Assignment02.service.impl;

import com.example.Assignment02.entity.*;
import com.example.Assignment02.repository.ICompanyRepository;
import com.example.Assignment02.repository.IFollowCompanyRepository;
import com.example.Assignment02.repository.IUserRepository;
import com.example.Assignment02.service.IFollowCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FollowCompanyService implements IFollowCompanyService {
    @Autowired
    private IFollowCompanyRepository followCompanyRepository;

    @Autowired
    private ICompanyRepository companyRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public FollowCompany findByUserId(int id) {
        return followCompanyRepository.findByUserId(id);
    }


    // Lấy ra danh sách Company theo User ID trong FollowCompany
    @Override
    public Page<FollowCompany> getListCompanyByUserId(Pageable pageable, int userId) {
        Page<FollowCompany> pages = followCompanyRepository.findByUserId(pageable, userId);
        return pages;
    }

    @Override
    public String saveCompany(int idCompany, int id) {
        FollowCompany followCompany = followCompanyRepository.findByUserIdAndCompanyId(id, idCompany);
        if (Objects.isNull(followCompany)) {
            //tao moi khoi tao save job
            FollowCompany followCompany1 = new FollowCompany();
            // tim re theo id, set RE cho saveJob
            Company company = companyRepository.findById(idCompany).get();
            if (Objects.isNull(company)) {
                throw new RuntimeException("Not Found");
            }
            followCompany1.setCompany(company);
            //tim user theo id
            User user = userRepository.findById(id).get();
            if (Objects.isNull(user)) {
                throw new RuntimeException("Not Found");
            }
            followCompany1.setUser(user);
            //save followCompany
            followCompanyRepository.save(followCompany1);
            return "save";
        } else {
            followCompanyRepository.delete(followCompany);
            return "un-save";
        }
    }

}
