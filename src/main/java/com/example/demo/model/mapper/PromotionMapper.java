package com.example.demo.model.mapper;


import com.example.demo.entity.Promotion;
import com.example.demo.model.request.CreatePromotionReq;

import static com.example.demo.config.Constant.DISCOUNT_PERCENT;

public class PromotionMapper {
    public static Promotion toProduct(CreatePromotionReq req) {
        Promotion promotion = new Promotion();
        promotion.setCouponCode(req.getCode());
        promotion.setName(req.getName());
        promotion.setExpiredAt(req.getExpiredDate());
        promotion.setActive(req.isActive());
        promotion.setPublic(req.isPublic());
        promotion.setDiscountType(req.getDiscountType());
        promotion.setDiscountValue(req.getDiscountValue());
        if (req.getDiscountType() == DISCOUNT_PERCENT) {
            promotion.setMaximumDiscountValue(req.getMaxValue());
        } else {
            promotion.setMaximumDiscountValue(req.getDiscountValue());
        }

        return promotion;
    }
}
