package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductSize;
import com.example.demo.model.dto.DetailProductInfoDto;
import com.example.demo.model.dto.PageableDto;
import com.example.demo.model.dto.ProductInfoDto;
import com.example.demo.model.dto.ShortProductInfoDto;
import com.example.demo.model.request.CreateProductReq;
import com.example.demo.model.request.FilterProductReq;
import com.example.demo.model.request.UpdateOnfeetImagesReq;
import com.example.demo.model.request.UpdateSizeCountReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public List<ProductInfoDto> getListBestSellerProduct();

    public List<ProductInfoDto> getListNewProduct();

    public List<ProductInfoDto> getListSuggestProduct();

    public DetailProductInfoDto getDetailProductById(String id);

    public List<ProductInfoDto> getRelatedProducts(String id);

    public List<Integer> getListAvailableSize(String id);

    public PageableDto filterProduct(FilterProductReq req);

    public PageableDto searchProductByKeyword(String keyword, Integer page);

    public PageableDto adminGetListProduct(String id, String name, String category, String brand, String order, String direction, int page);

    public String createProduct(CreateProductReq req);

    public Product getProductById(String id);

    public void updateProduct(String id, CreateProductReq req);

    public void updateOnfeetImages(String id, UpdateOnfeetImagesReq req);

    public void updateSizeCount(UpdateSizeCountReq req);

    public void deleteProduct(String id);

    public List<ProductSize> getListSizeOfProduct(String id);

    public List<ShortProductInfoDto> getAllProduct();

    public List<ShortProductInfoDto> getAvailableProducts();

    public boolean checkProductSizeAvailable(String productId, int size);
    //Đếm số lượng sản phẩm
    long getCountProduct();
}
