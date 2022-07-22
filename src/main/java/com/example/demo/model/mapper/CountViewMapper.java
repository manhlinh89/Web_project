package com.example.demo.model.mapper;

import com.example.demo.entity.Views;
import com.example.demo.model.dto.DetailViewByProductIdDto;

public class CountViewMapper {
    public static DetailViewByProductIdDto detailViewByProductIdDto(Views count_view){
        DetailViewByProductIdDto dv = new DetailViewByProductIdDto();
        dv.setCount_view(count_view.getCount_view());
        dv.setProduct_id(count_view.getProduct_id());
        return dv;
    }
}
