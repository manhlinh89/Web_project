package com.example.demo.model.dto;


import com.example.demo.entity.Brand;
import com.example.demo.entity.Comment;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetailProductInfoDto {
    private String id;

    private String name;

    private String slug;

    private long price;

    private int totalSold;

    private ArrayList<String> productImages;

    private ArrayList<String> onfeetImages;

    private long promotionPrice;

    private String couponCode;

    private String description;

    private Brand brand;
    private List<Comment> comments;


}
