package com.example.demo.controller.admin;


import com.example.demo.entity.Category;
import com.example.demo.model.dto.CategoryInfo;
import com.example.demo.model.request.CreateCategoryReq;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ManageCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/categories")
    public String getCategoryManagePage(Model model) {

        List<CategoryInfo> categories =categoryService.getListCategoryAndProductCount();
        model.addAttribute("categories",categories);
        return "admin/category/list";
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryReq req ){
        System.out.println("========= Đang call đến API controler Tạo mới Category =====");
        Category category = categoryService.createCategory(req);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/api/admin/categories/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        System.out.println("========= Đang call API controller Xoá Category ========");
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Xoá thành công");
    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CreateCategoryReq req, @PathVariable int id){
        categoryService.updateCategory(id, req);
        return ResponseEntity.ok("Cập nhật thành công");

    }

}
