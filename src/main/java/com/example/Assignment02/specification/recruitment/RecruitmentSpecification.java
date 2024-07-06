package com.example.Assignment02.specification.recruitment;

import com.example.Assignment02.entity.Recruitment;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@SuppressWarnings("serial")
@AllArgsConstructor
public class RecruitmentSpecification implements Specification<Recruitment> {

    @NonNull
    private String field;
    @NonNull
    private Object value;

    @Override
    public Predicate toPredicate(Root<Recruitment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (field.equalsIgnoreCase("status")) {
            return criteriaBuilder.equal(root.get("status"),  Integer.parseInt(value.toString()) );
        }
        if (field.equalsIgnoreCase("nameCompany")) {
            return criteriaBuilder.like(root.get("company").get("nameCompany"), "%" + value.toString() + "%");
        }

        if (field.equalsIgnoreCase("address")) {
            return criteriaBuilder.like(root.get("company").get("address"), "%" + value.toString() + "%");
        }
        if (field.equalsIgnoreCase("title")) {
            return criteriaBuilder.like(root.get("title"), "%" + value.toString() + "%");
        }
        return null;
    }
}
