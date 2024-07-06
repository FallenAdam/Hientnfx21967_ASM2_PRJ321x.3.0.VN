package com.example.Assignment02.repository;

import com.example.Assignment02.entity.FollowCompany;
import com.example.Assignment02.entity.SaveJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFollowCompanyRepository extends JpaRepository<FollowCompany, Integer> {

    FollowCompany findByUserId(int userId);

    Page<FollowCompany> findByUserId(Pageable pageable, int id);

    FollowCompany findByUserIdAndCompanyId(int id, int idCompany);
}
