package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.request.ChangePasswordReq;
import com.example.demo.model.request.CreateUserReq;
import com.example.demo.model.request.UpdateProfileReq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User createUser(CreateUserReq req);

    public void changePassword(User user, ChangePasswordReq req);

    public User updateProfile(User user, UpdateProfileReq req);
    Page<User> adminListUserPages(String fullName, String phone, String email, Integer page);
    List<UserDto> getListUsers();
}
