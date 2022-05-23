package com.example.demo.service;

import com.example.demo.entity.Brand;
import com.example.demo.model.dto.BrandInfo;
import com.example.demo.model.request.CreateBrandReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {
    public List<Brand> getListBrand();

    public List<BrandInfo> getListBrandAndProductCount();

    public Brand createBrand(CreateBrandReq req);

    public void updateBrand(int id, CreateBrandReq req);

    public void deleteBrand(int id);

    long getCountBrands();
}
