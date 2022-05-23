package com.example.demo.service.Impl;

import com.example.demo.entity.Promotion;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.ProductInfoDto;
import com.example.demo.model.mapper.PromotionMapper;
import com.example.demo.model.request.CreatePromotionReq;
import com.example.demo.repository.PromotionRepository;
import com.example.demo.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static com.example.demo.config.Constant.DISCOUNT_PERCENT;

@Component
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<ProductInfoDto> checkPublicPromotion(List<ProductInfoDto> products) {
        List<ProductInfoDto> rs = products;

        //check has promotion
        Promotion promotion = promotionRepository.checkHasPublicPromotion();
        if(promotion != null) {
            // Tinh toan gia khuyyen mai
            for (ProductInfoDto product : products) {
                long discountValue = promotion.getMaximumDiscountValue();
                if(promotion.getDiscountType() == DISCOUNT_PERCENT) {
                    long tmp = product.getPrice() * promotion.getDiscountValue() /100;
                    if(tmp < discountValue){
                        discountValue = tmp;
                    }
                }
                long promotionPrice = product.getPrice() - discountValue;
                if(promotionPrice > 0) {
                    product.setPromotionPrice(promotionPrice);
                } else {
                    product.setPromotionPrice(0);
                }
            }

        }


        return rs;
    }

    @Override
    public Promotion checkPublicPromotion() {
        Promotion promotion = promotionRepository.checkHasPublicPromotion();
        return promotion;
    }

    @Override
    public Promotion checkPromotion(String code) {
        Promotion promotion = promotionRepository.checkPromotion(code);
        return promotion;
    }

    @Override
    public long calculatePromotionPrice(Long price, Promotion promotion) {
        long discountValue = promotion.getMaximumDiscountValue();
        long tmp = promotion.getDiscountValue();
        System.out.println("------------------ in Total Before: " + tmp);
        if (promotion.getDiscountType() == DISCOUNT_PERCENT) {
            tmp = price * promotion.getDiscountValue() / 100;
        }
        if (tmp < discountValue) {
            discountValue = tmp;
        }

        long promotionPrice = price - discountValue;
        if (promotionPrice < 0) {
            promotionPrice = 0;
        }
        System.out.println("------------------ in TOtalprice: " + tmp);
        System.out.println("------------------ in TOtalprice: " + price);
       System.out.println("------------------ in DiscountValue: " + discountValue);
        System.out.println("------------------ in Caculatot: " + promotionPrice);
        return promotionPrice;
    }

    @Override
    public Page<Promotion> adminGetListPromotion(String code, String name, String ispublic, String active, int page) {
        page--;
        if(page < 0){
            page = 0;
        }
        Page<Promotion> rs = promotionRepository.adminGetListPromotion(code,name,ispublic,active, PageRequest.of(page,15));
        return rs;
    }

    @Override
    public Promotion createPromotion(CreatePromotionReq req) {
        //validate
        Optional<Promotion> rs = promotionRepository.findByCouponCode(req.getCode());
        System.out.println("Giá tri mã code giảm giá : " + req.getCode());
        if (rs.isPresent()) {
            throw new BadRequestException("Mã code đã tồn tại trong hệ thống");
        }
        // Validate
        System.out.println("Giá trị discountValue: " + req.getDiscountValue());
        System.out.println("Giá trị getMaxvalue: " + req.getMaxValue());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (req.getExpiredDate().before(now)) {
            throw new BadRequestException("Hạn khuyến mãi không hợp lệ");
        }
        if (req.getDiscountType() == DISCOUNT_PERCENT) {
            if (req.getDiscountValue() < 1 || req.getDiscountValue() > 100) {
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMaxValue() < 1000) {
                throw new BadRequestException("1. Mức giảm tối đa phải lớn hơn 1000");
            }
        } else {
            if (req.getDiscountValue() < 1000) {
                throw new BadRequestException("2. Mức giảm giá phải lớn hơn 1000 ");
            }
        }


        // Check has valid promotion
        if (req.isPublic() && req.isActive()) {
            Promotion alreadyPromotion = promotionRepository.checkHasPublicPromotion();
            if (alreadyPromotion != null) {
                throw new BadRequestException("Chương trình khuyến mãi công khai \""+alreadyPromotion.getCouponCode()+"\" đang chạy. Không thể tạo mới");
            }
        }

        //Promotion promotion = PromotionMapper.toProduct(req);
        Promotion promotion = new Promotion();
        promotion.setCouponCode(req.getCode());
        promotion.setName(req.getName());
        promotion.setActive(req.isActive());
        promotion.setPublic(req.isPublic());
        promotion.setExpiredAt(req.getExpiredDate());
        promotion.setDiscountType(req.getDiscountType());
        promotion.setDiscountValue(req.getDiscountValue());
        promotion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        if (req.getDiscountType() == DISCOUNT_PERCENT) {
            promotion.setMaximumDiscountValue(req.getMaxValue());
        } else {
            promotion.setMaximumDiscountValue(req.getDiscountValue());
        }
        promotion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        promotionRepository.save(promotion);

        return promotion;
    }

    @Override
    public void updatePromotion(long id, CreatePromotionReq req) {
        //check exits promoton

        Optional<Promotion> rs = promotionRepository.findById(id);
        if(rs == null ) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }
        //validate req
        if(req.getDiscountType() == DISCOUNT_PERCENT) {

            if(req.getDiscountValue() < 1 || req.getDiscountValue() > 100){
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMaxValue() < 1000) {
                throw new BadRequestException("Mức giảm tối đa phải lớn hơn 1000");
            }
        }else {
            if (req.getDiscountValue() < 1000) {
                throw new BadRequestException("Mức giảm giá phải lớn hơn 1000 ");
            }
        }

        // Check has valid promotion
        if (req.isPublic() && req.isActive()) {
            Promotion alreadyPromotion = promotionRepository.checkHasPublicPromotion();
            if (alreadyPromotion != null && alreadyPromotion.getId() != id) {
                throw new BadRequestException("Chương trình khuyến mãi công khai \""+alreadyPromotion.getCouponCode()+"\" đang chạy. Không thể tạo mới");
            }
        }
        Promotion promotion = PromotionMapper.toProduct(req);
        promotion.setCreatedAt(rs.get().getCreatedAt());
        promotion.setId(id);

        try {
            promotionRepository.save(promotion);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật khuyến mãi");
        }

    }

    @Override
    public void deletePromotion(long id) {
        //check exits promotion
        Optional<Promotion> rs = promotionRepository.findById(id);
        if(rs == null) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }
        //check promotion đang sử dụng
        int countUse = promotionRepository.checkPromotionInUse(rs.get().getCouponCode());
        if(countUse > 0) {
            throw new BadRequestException("Khuyến mãi đã được sử dụng. không thể xoá");
        }
        try {
            promotionRepository.deleteById(id);
        }catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xoá khuyến mãi");
        }
    }

    @Override
    public Promotion getPromotionById(long id) {
        Optional<Promotion> rs = promotionRepository.findById(id);
        if (rs == null) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        return rs.get();
    }

    @Override
    public List<Promotion> getAllValidPromotion() {
         return promotionRepository.getAllValidPromotion();
    }
}
