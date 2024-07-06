package com.example.Assignment02.repository;

import com.example.Assignment02.entity.Recruitment;
import com.example.Assignment02.entity.SaveJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaveJobRepository extends JpaRepository<SaveJob, Integer> {

    SaveJob findByUserIdAndRecruitmentId(int userID, int reId);

//    SaveJob findByUserId(int id);

    List<SaveJob> findByUserId(int id);
    Page<SaveJob> findByUserId(Pageable pageable, int id);
}
