package com.example.Assignment02.service.impl;

import com.example.Assignment02.dto.ICategoryDTO;
import com.example.Assignment02.entity.Category;
import com.example.Assignment02.entity.Company;
import com.example.Assignment02.repository.ICategoryRepository;
import com.example.Assignment02.repository.ICompanyRepository;
import com.example.Assignment02.service.ICategoryService;
import com.example.Assignment02.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private ICompanyRepository companyRepository;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ICategoryDTO> getCategoryInHome() {
        return categoryRepository.getCategoryInHome();
    }

    @Override
    public Category findById(int id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (!optional.isPresent()){
            throw  new RuntimeException("Not Found ID");
        }
        return optional.get();
    }

    @Override
    public Company findByUserId(int id) {
        Optional<Company> optional = companyRepository.findById(id);
        if (!optional.isPresent()){
            throw  new RuntimeException("Not Found ID");
        }
        return optional.get();
    }
}
