package com.example.Assignment02.repository;

import com.example.Assignment02.dto.ICompanyDTO;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICompanyRepository extends JpaRepository<Company, Integer> , JpaSpecificationExecutor<Company> {

    @Query(value = "select co.*, count(distinct ca.id) as svtut  from category ca\n" +
            "join recruitment re on ca.id = re.CATEGORY_ID\n" +
            "join company co on co.id = re.COMPANY_ID\n" +
            "join apply_post ap on ap.RECRUITMENT_ID = re.id\n" +
            "group by co.id\n" +
            "order by  max(re.QUANTITY) desc, count(ap.id) desc limit 1;", nativeQuery = true)
    public ICompanyDTO getCompanieInHome();

    Company findByUserId(int id);
}
