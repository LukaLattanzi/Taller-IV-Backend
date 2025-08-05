package com.talleriv.Backend.service;

import com.talleriv.Backend.dto.CategoryDTO;
import com.talleriv.Backend.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id, CategoryDTO categoryDTO);
    Response deleteCategory(Long id);
}