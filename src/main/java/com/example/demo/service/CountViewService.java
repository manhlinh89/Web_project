package com.example.demo.service;

import com.example.demo.entity.Views;
import com.example.demo.model.dto.DetailViewByProductIdDto;
import com.example.demo.model.request.CreateBrandReq;
import com.example.demo.model.request.CreateCountViewReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountViewService {
    public long getCountView(String product_id);
    public Views CreateCountView(CreateCountViewReq req);
    public DetailViewByProductIdDto getViewByProductId(String product_id);
    public List<Views> getListView ();

}
