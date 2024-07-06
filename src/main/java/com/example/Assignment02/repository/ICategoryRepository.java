package com.example.Assignment02.repository;

import com.example.Assignment02.dto.ICategoryDTO;
import com.example.Assignment02.dto.ICompanyDTO;
import com.example.Assignment02.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select ca.*,co.ADDRESS, co.NAME_COMPANY from category ca\n" +
            "join company co on ca.id = co.id\n" +
            "group by  ca.id\n" +
            "order by ca.NUMBER_CHOOSE desc limit 4;", nativeQuery = true)
    List<ICategoryDTO> getCategoryInHome();
}
