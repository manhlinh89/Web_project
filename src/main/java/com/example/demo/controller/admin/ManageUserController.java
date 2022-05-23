package com.example.demo.controller.admin;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageUserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/users")
    public String getUserManagePage(Model model,
                                    @RequestParam(defaultValue = "", required = false) String fullName,
                                    @RequestParam(defaultValue = "", required = false) String phone,
                                    @RequestParam(defaultValue = "", required = false) String email,
                                    @RequestParam(defaultValue = "", required = false) String address,
                                    @RequestParam(defaultValue = "1", required = false) Integer page) {
        //TO do..................
        Page<User> users = userService.adminListUserPages(fullName, phone, email, page);
        System.out.println("================Giá trị total page: " + users.getTotalPages());
        model.addAttribute("users", users.getContent());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", users.getPageable().getPageNumber() + 1);

        return "/admin/user/list";
    }

    @GetMapping("/admin/users/create")
    public String getProductCreatePage(Model model){

        return "/admin/user/create";
    }
}
