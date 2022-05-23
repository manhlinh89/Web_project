package com.example.demo.service;

import com.example.demo.entity.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public void save(Image img);

    public List<String> getListImageOfUser(long id);

    public void deleteImage(String uploadDir, String filename);
}
