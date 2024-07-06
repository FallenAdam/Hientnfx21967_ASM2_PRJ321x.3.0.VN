package com.example.Assignment02.service;

import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.SaveJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISaveJobService {
    String saveJob(int idRe, int id);

    Page<Recruitment> getListRecruitmentByUserId(Pageable pageable, int id);





}
