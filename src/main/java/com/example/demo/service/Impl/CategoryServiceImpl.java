package com.example.demo.service.Impl;

import com.example.demo.entity.Category;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.CategoryInfo;
import com.example.demo.model.request.CreateCategoryReq;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getListCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<CategoryInfo> getListCategoryAndProductCount() {
        return categoryRepository.getListCategoryAndProductCount();
    }

    @Override
    public Category createCategory(CreateCategoryReq req) {
        Category category = new Category();
        category.setName(req.getName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public void updateCategory(int id, CreateCategoryReq req) {
        //check exits category in Cơ sở dữ liệu
        Optional<Category> rs = categoryRepository.findById(id);
        if(rs == null){
            throw new NotFoundException("Category không tồn tại!");
        }
        Category category = rs.get();
        category.setName(req.getName());
        try{
            categoryRepository.save(category);
        }catch (Exception e) {
            throw new InternalServerException("Lỗi khi chỉnh sủa category");
        }

    }

    @Override
    public void deleteCategory(int id) {
        //check exits category trong cơ sở dữ liệu
        // Check category exist
        Optional<Category> rs = categoryRepository.findById(id);
        if (rs== null) {
            throw new NotFoundException("Category không tồn tại");
        }
        System.out.println("=============== Đang ở trong delete category++++++++++: ");
        // Check product in category
        int count = categoryRepository.checkProductInCategory(id);
        System.out.println("=============== Giá trị count: " + count);
        if (count == 1) {
            throw new BadRequestException("Có sản phẩm thuộc category không thể xóa");
        }
        Category category = rs.get();
        System.out.println("===============" +rs.get().getName());

        try {
            categoryRepository.delete(category);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa category");
        }

    }

    @Override
    public long getCountCategories() {
        return categoryRepository.count();
    }
}
