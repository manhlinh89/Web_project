package com.example.demo.service.Impl;

import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.DuplicateRecordException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.ChangePasswordReq;
import com.example.demo.model.request.CreateUserReq;
import com.example.demo.model.request.UpdateProfileReq;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.Constant.LIMIT_USER;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(CreateUserReq req) {
        // Check email exist
        User user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email is already in use");
        }

        user = UserMapper.toUser(req);
        userRepository.save(user);

        return user;
    }

    @Override
    public void changePassword(User user, ChangePasswordReq req) {
        //Kiểm tra mật khẩu
        if (!BCrypt.checkpw(req.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        String hash = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt(12));
        user.setPassword(hash);
        userRepository.save(user);
    }

    @Override
    public User updateProfile(User user, UpdateProfileReq req) {
        user.setFullName(req.getFullName());
        user.setPhone(req.getPhone());
        user.setAddress(req.getAddress());

        return userRepository.save(user);
    }

    @Override
    public Page<User> adminListUserPages(String fullName, String phone, String email, Integer page) {
        page--;
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, LIMIT_USER, Sort.by("created_at").descending());
        return userRepository.adminListUserPages(fullName, phone, email, pageable);
    }

    @Override
    public List<UserDto> getListUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDTOS = new ArrayList<>();
        for(User user : users){
            userDTOS.add(UserMapper.toUserDto(user));
        }
        return userDTOS;
    }
}
