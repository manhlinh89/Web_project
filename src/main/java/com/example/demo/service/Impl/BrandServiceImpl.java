package com.example.demo.service.Impl;

import com.example.demo.entity.Brand;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.BrandInfo;
import com.example.demo.model.request.CreateBrandReq;
import com.example.demo.repository.BrandRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Brand> getListBrand() {
        return brandRepository.findAll();
    }

    @Override
    public List<BrandInfo> getListBrandAndProductCount() {
        return brandRepository.getListBrandAndProductCount();
    }

    @Override
    public Brand createBrand(CreateBrandReq req) {
        Brand brand = new Brand();
        brand.setName(req.getName());
        brand.setThumbnail(req.getThumbnail());
        brandRepository.save(brand);
        return brand;
    }

    @Override
    public void updateBrand(int id, CreateBrandReq req) {
        //check brand exits
        Optional<Brand> rs = brandRepository.findById(id);
        if(rs == null) {
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }
        //Lấy ra Brand từ truy vấn
        Brand brand = rs.get();
        brand.setName(req.getName());
        brand.setThumbnail(req.getThumbnail());
        try {
            brandRepository.save(brand);
        }catch (Exception e) {
            throw new InternalServerException("Lỗi khi update nhãn hiệu");
        }
    }

    @Override
    public void deleteBrand(int id) {
        //check brand exit
        Optional<Brand> rs  = brandRepository.findById(id);
        if(rs == null){
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }
        // Check product in brand
        int count  =productRepository.countByBrandId(id);
        if (count > 0) {
            throw new BadRequestException("Có sản phẩm thuộc nhãn hiệu không thể xóa");
        }
        Brand brand = rs.get();

        try {
            brandRepository.delete(brand);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa nhãn hiệu");
        }
    }

    @Override
    public long getCountBrands() {
        return brandRepository.count();
    }
}
