package com.example.demo.service.Impl;

import com.example.demo.entity.Image;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.InternalServerException;
import com.example.demo.repository.ImageRepository;
import com.example.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;
@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Override
    public void save(Image img) {
        imageRepository.save(img);
    }

    @Override
    @Transactional(rollbackFor = InternalServerException.class)
    public List<String> getListImageOfUser(long id) {
        List<String> images = imageRepository.getListImageOfUser(id);
        return images;
    }

    @Override
    @Transactional(rollbackFor = InternalServerException.class)
    public void deleteImage(String uploadDir, String filename) {
        String link = "/media/static/" + filename;
        Image img = imageRepository.findByLink(link);
        if(img == null) {
            throw new BadRequestException("File không tồn tại");
        }
        Integer inUse = imageRepository.checkImgInUse(link);
        if(inUse != null) {
            throw new BadRequestException("ảnh đã được sử dụng không thể xoá");
        }
        imageRepository.delete(img);
        File file = new File(uploadDir + "/" + filename);
        if(file.exists()){
            if(!file.delete()){
                throw new InternalServerException("lỗi khi xoá ảnh");
            }
        }
    }
}
