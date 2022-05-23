package com.example.demo.repository;

import com.example.demo.entity.Brand;
import com.example.demo.model.dto.BrandInfo;
import com.example.demo.model.dto.ChartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(nativeQuery = true, name = "getListBrandAndProductCount")
    public List<BrandInfo> getListBrandAndProductCount();

    @Query(name = "getProductOrderBrands",nativeQuery = true)
    List<ChartDto> getProductOrderBrands();
}
