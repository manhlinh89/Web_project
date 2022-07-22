package com.example.demo.service.Impl;


import com.example.demo.entity.Views;
import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.dto.DetailViewByProductIdDto;
import com.example.demo.model.mapper.CountViewMapper;
import com.example.demo.model.request.CreateBrandReq;
import com.example.demo.model.request.CreateCountViewReq;
import com.example.demo.repository.ViewRepository;
import com.example.demo.service.CountViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.View;
import java.util.List;
import java.util.Optional;

@Component
public class CountViewServiceImpl implements CountViewService {

    @Autowired
    ViewRepository viewRepository;

    @Override
    public long getCountView(String product_id) {
        Optional<Views> result = viewRepository.findById(product_id);
        if(result == null){
            return 0;
        }
        Views view = result.get();
        if(view.getCount_view() < 0){
            return 0;
        }

        return view.getCount_view();
    }

    @Override
    public Views CreateCountView(CreateCountViewReq req) {
        Views view = new Views();
        view.setProduct_id(req.getProduct_id());
        view.setCount_view(req.getCount_view());
        viewRepository.save(view);
        return view;
    }

    @Override
    public DetailViewByProductIdDto getViewByProductId(String product_id) {
        Optional<Views> result = viewRepository.findById(product_id);

        if(result == null){
            return null;
        }
        Views count_view = result.get();
        DetailViewByProductIdDto rs = CountViewMapper.detailViewByProductIdDto(count_view);
        return rs;
    }

    @Override
    public List<Views> getListView() {
        return viewRepository.findAll();
    }
}
