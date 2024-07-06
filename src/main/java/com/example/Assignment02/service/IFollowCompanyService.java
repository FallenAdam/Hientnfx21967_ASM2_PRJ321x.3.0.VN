package com.example.Assignment02.service;

import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.SaveJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFollowCompanyService {
    FollowCompany findByUserId(int id);


    // Lấy ra danh sách Company theo User ID trong FollowCompany
    Page<FollowCompany> getListCompanyByUserId(Pageable pageable, int id);

    String saveCompany(int idCompany, int id);
}
