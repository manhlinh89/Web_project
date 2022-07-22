package com.example.demo.controller.admin;

import com.example.demo.entity.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.PageableDto;
import com.example.demo.model.request.CreateProductReq;
import com.example.demo.model.request.UpdateOnfeetImagesReq;
import com.example.demo.model.request.UpdateSizeCountReq;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

import static com.example.demo.config.Constant.SIZE_VN;

@Controller
public class ManageProductController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @GetMapping("/admin/products")
    public String getProductManagePage(Model model,
                                       @RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "product.created_at") String order,
                                       @RequestParam(defaultValue = "desc") String direction,
                                       @RequestParam(defaultValue = "") String Id,
                                       @RequestParam(defaultValue = "") String name,
                                       @RequestParam(defaultValue = "%%") String brand,
                                       @RequestParam(defaultValue = "%%") String category) {
        if (!direction.toLowerCase().equals("desc")) {
            direction = "asc";
        }
        // Get list category
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);
        //Get List brand
        List<Brand>brands = brandService.getListBrand();
        model.addAttribute("brands", brands);

        //Get list products
        PageableDto rs =productService.adminGetListProduct(Id, name, category, brand,order, direction, page);
        model.addAttribute("products", rs.getItems());
        model.addAttribute("totalPages", rs.getTotalPages());
        model.addAttribute("currentPage", rs.getCurrentPage());
        return "/admin/product/list";
    }

    @GetMapping("/admin/products/create")
    public String getProductCreatePage(Model model){
        //Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images",images);
        // Get list category
        List<Category> categories = categoryService.getListCategory();
        model.addAttribute("categories", categories);

        //get list brand
         List<Brand> brands = brandService.getListBrand();
         model.addAttribute("brands",brands);
        return "/admin/product/create";
    }

    @PostMapping("/api/admin/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductReq req) {
        String productId = productService.createProduct(req);
        //-------- Todo add luôn lượt view ban đầu của sản phẩm gán giá trị bằng 0

        //--------------------------------end add ---------------------------------------
        return ResponseEntity.ok(productId);
    }

    // Lấy thông tin chi tiết sản phẩm
    @GetMapping("/admin/products/{id}")
    public String getDetailProductPage(Model model, @PathVariable String id) {


        try{
            // Get info
            Product product =  productService.getProductById(id);
            model.addAttribute("product", product);
            System.out.println(product.getCategories());

            //Get list image of user
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            List<String> images = imageService.getListImageOfUser(user.getId());
            model.addAttribute("images", images);

            //get List category
            List<Category> categories = categoryService.getListCategory();
            model.addAttribute("categories", categories);

            //get list brand
            List<Brand> brands = brandService.getListBrand();
            model.addAttribute("brands", brands);
            // get list size

            model.addAttribute("sizeVn", SIZE_VN);
            //Get list size of product
            List<ProductSize> productSizes = productService.getListSizeOfProduct(id);
            model.addAttribute("productSizes", productSizes);
            return "admin/product/detail";

        }catch (NotFoundException ex){
            return "error/admin";
        }
    }

    @PutMapping("/api/admin/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody CreateProductReq req) {
        productService.updateProduct(id, req);
        return ResponseEntity.ok("Cập nhập thành công");
    }

    //cập nhật image onfeet
    @PutMapping("/api/admin/products/{id}/update-onfeet-image")
    public ResponseEntity<?> updateOnfeetImages(@PathVariable String id, @Valid @RequestBody UpdateOnfeetImagesReq req){
        productService.updateOnfeetImages(id, req);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    //update số lượng size
    @PutMapping("/api/admin/products/update-size-count")
    public ResponseEntity<?> updateSizeCount(@Valid @RequestBody UpdateSizeCountReq req){
        productService.updateSizeCount(req);
        return ResponseEntity.ok("Cập nhật thành công");
    }

    // Xoá sản phẩm
    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Xoá thành công");
    }


}
