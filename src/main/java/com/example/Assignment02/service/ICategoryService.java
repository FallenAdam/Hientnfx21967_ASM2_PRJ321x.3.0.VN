package com.example.Assignment02.service;

import com.example.Assignment02.dto.ICategoryDTO;
import com.example.Assignment02.entity.Category;
import com.example.Assignment02.entity.Company;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategory();

   List<ICategoryDTO> getCategoryInHome();

    Category findById(int id);

    Company findByUserId(int id);
}
