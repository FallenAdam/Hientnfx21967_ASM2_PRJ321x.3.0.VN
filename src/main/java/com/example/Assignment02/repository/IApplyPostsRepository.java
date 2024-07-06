package com.example.Assignment02.repository;

import com.example.Assignment02.entity.ApplyPosts;
import com.example.Assignment02.entity.SaveJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IApplyPostsRepository extends JpaRepository<ApplyPosts, Integer> {
    ApplyPosts findByUserIdAndRecruitmentId(int userID, int reId);


}
