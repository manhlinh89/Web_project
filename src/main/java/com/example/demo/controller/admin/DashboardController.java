package com.example.demo.controller.admin;


import com.example.demo.model.dto.ChartDto;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/admin")
    public String getDashboardPage(Model model){
        return "admin/dashboard";
    }
    @GetMapping("/api/admin/count/posts")
    public ResponseEntity<Object> getCountPost(){
        long countPosts = blogService.getCountPost();
        return ResponseEntity.ok(countPosts);
    }

    @GetMapping("/api/admin/count/products")
    public ResponseEntity<Object> getCountProduct(){
        long countProducts = productService.getCountProduct();
        return ResponseEntity.ok(countProducts);
    }

    @GetMapping("/api/admin/count/orders")
    public ResponseEntity<Object> getCountOrders(){
        long countOrders = orderService.getCountOrder();
        return ResponseEntity.ok(countOrders);
    }

    @GetMapping("/api/admin/count/categories")
    public ResponseEntity<Object> getCountCategories(){
        long countCategories = categoryService.getCountCategories();
        return ResponseEntity.ok(countCategories);
    }

    @GetMapping("/api/admin/count/brands")
    public ResponseEntity<Object> getCountBrands(){
        long countBrands = brandService.getCountBrands();
        return ResponseEntity.ok(countBrands);
    }

    @GetMapping("/api/admin/count/users")
    public ResponseEntity<Object> getCountUsers(){
        long countUsers = userRepository.count();
        return ResponseEntity.ok(countUsers);
    }

    @GetMapping("/api/admin/product-order-categories")
    public ResponseEntity<Object> getListProductOrderCategories(){
        List<ChartDto> chartDTOS = categoryRepository.getListProductOrderCategories();
        return ResponseEntity.ok(chartDTOS);
    }

    @GetMapping("/api/admin/product-order-brands")
    public ResponseEntity<Object> getProductOrderBrands(){
        List<ChartDto> chartDTOS = brandRepository.getProductOrderBrands();
        return ResponseEntity.ok(chartDTOS);
    }

    @GetMapping("/api/admin/product-order")
    public ResponseEntity<Object> getProductOrder(){
        Pageable pageable = PageRequest.of(0,10);
        Date date = new Date();
        System.out.println("======================+++++" + date.getMonth());
        System.out.println("======================" + date.getYear());
        // comment hàm cũ
        //List<ChartDto> chartDTOS = productRepository.getProductOrders(pageable, date.getMonth()+1, date.getYear() + 1900);
        //Hàm mới không cần truyền tham số data
        List<ChartDto> chartDTOS = productRepository.getProductOrders_test(pageable, date.getMonth()+1, date.getYear() + 1900);

        return ResponseEntity.ok(chartDTOS);
    }
}
