package com.example.demo.service;

import com.example.demo.entity.Brand;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.model.dto.BrandInfo;
import com.example.demo.model.dto.PageableDto;
import com.example.demo.model.request.CreateBrandReq;
import com.example.demo.model.request.CreatePostReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    public Post createPost(CreatePostReq req, User user);

    public void updatePost(CreatePostReq req, User user, long id);

    public void deletePost(long id);

    public Page<Post> getListPost(int page);

    public Post getPostById(long id);

    public List<Post> getLatestPostsNotId(long id);

    public List<Post> getLatestPost();

    public PageableDto adminGetListPost(String title, String status, int page, String order, String direction);

    long getCountPost();

    interface BrandService {
        public List<Brand> getListBrand();
        public List<BrandInfo> getListBrandAndProductCount();
        public Brand createBrand(CreateBrandReq req);
        public void updateBrand(int id, CreateBrandReq req);
        public void deleteBrand(int id);
    }
}
