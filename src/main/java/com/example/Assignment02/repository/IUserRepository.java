package com.example.Assignment02.repository;

import com.example.Assignment02.entity.User;
import com.example.Assignment02.form.UpdateUserProfileForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findByCvId(int id);




}
