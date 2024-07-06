package com.example.Assignment02.repository;

import com.example.Assignment02.dto.IRecruitmentDTO;
import com.example.Assignment02.entity.Recruitment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRecruitmentRepository extends JpaRepository<Recruitment , Integer>, JpaSpecificationExecutor<Recruitment> {

    @Query(value = "select re.*, co.NAME_COMPANY as companyName  from category ca\n" +
            "join recruitment re on ca.id = re.CATEGORY_ID\n" +
            "join company co on co.id = re.COMPANY_ID\n" +
            "join apply_post ap on ap.RECRUITMENT_ID = re.id\n" +
            "group by re.id\n" +
            "order by count(ap.id) desc, re.SALARY desc limit 1;", nativeQuery = true)
    public IRecruitmentDTO getRecruitmentInHome();

    Page<Recruitment> findByCompanyId(Pageable pageable, int companyId);

    @Query(value = "select * from recruitment re\n" +
            "join company co on re.COMPANY_ID = co.ID\n" +
            "where co.ADDRESS like concat('%',:key, '%') \n" +
            "limit :size offset :page", nativeQuery = true)
    List<Recruitment> findByCompanyAddress(int page, int size, String key);

    List<Recruitment> findByCompanyAddress(String address);



}
