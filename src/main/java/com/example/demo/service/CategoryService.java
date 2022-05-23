package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.model.dto.CategoryInfo;
import com.example.demo.model.request.CreateCategoryReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public List<Category> getListCategory();

    public List<CategoryInfo> getListCategoryAndProductCount();

    public Category createCategory(CreateCategoryReq req);

    public void updateCategory(int id, CreateCategoryReq req);

    public void deleteCategory(int id);
    //Đếm số danh mục
    long getCountCategories();

}
