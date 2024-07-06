package com.example.Assignment02.repository;

import com.example.Assignment02.entity.Cv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICvRepository extends JpaRepository<Cv, Integer> {
    void deleteById(Cv cv);
    Cv findByUserId(int id);
}
