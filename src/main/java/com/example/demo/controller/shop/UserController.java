package com.example.demo.controller.shop;

import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.dto.OrderDetailDto;
import com.example.demo.model.dto.OrderInfoDto;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.mapper.UserMapper;
import com.example.demo.model.request.AuthenticateReq;
import com.example.demo.model.request.CreateUserReq;
import com.example.demo.model.request.LoginReq;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.JwtTokenUtil;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static com.example.demo.config.Constant.*;

@Controller
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @PostMapping("/api/register")
    public ResponseEntity<?> createUser( @RequestBody CreateUserReq req) {
        User result = userService.createUser(req);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req, HttpServletResponse response) {
        // Xác thực từ username và password.
        // Authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword()
                    )
            );

            // Gen token
            String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

            // Add token to cookie to login
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            System.out.println("--eee------------fdsfdsfsdf:--sssss---------- " + req.getPassword());
            cookie.setMaxAge(MAX_AGE_COOKIE);
            cookie.setPath("/");
            System.out.println("--hhhhhhhhhhhhhhhhhhhhhhhhh------------------" + req.getPassword());
            response.addCookie(cookie);
            System.out.println("--ggggggg------------fdsfdsfsdf:--sssss---------- " + UserMapper.toUserDto(((CustomUserDetails) authentication.getPrincipal()).getUser()).getEmail());
            return ResponseEntity.ok(UserMapper.toUserDto(((CustomUserDetails) authentication.getPrincipal()).getUser()));
        } catch (Exception ex) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        }

    }
    @GetMapping("/tai-khoan")
    public String getProfilePage(Model model) {
        return "account/account";
    }
    @GetMapping("/tai-khoan/lich-su-giao-dich")
    public String getOrderHistoryPage(Model model){
        // Get list order pending
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<OrderInfoDto> orders = orderService.getListOrderOfPersonByStatus(ORDER_STATUS, user.getId());
        model.addAttribute("orders", orders);

        return "account/order_history";
    }

    @GetMapping("/tai-khoan/lich-su-giao-dich/{id}")
    public String getDetailOrderPage(Model model, @PathVariable int id){
        // lấy thông tin đăng nhập của user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        OrderDetailDto order = orderService.userGetDetailById(id, user.getId());
        if (order == null) {
            return "error/404";
        }
        model.addAttribute("order", order);

        if (order.getStatus() == ORDER_STATUS) {
            model.addAttribute("canCancel", true);
        } else {
            model.addAttribute("canCancel", false);
        }

        return "account/order_detail";

    }
    @GetMapping("/api/get-order-list")
    public ResponseEntity<?> getListOrderByStatus(@RequestParam int status){
        // check xem giá trị của status có hợp lệ không
        if(!LIST_ORDER_STATUS.contains(status)){
            throw new BadRequestException("Trạng thái đơn hàng không hợp lệ");
        }
        //Lấy ra thông tin đăng nhập của user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<OrderInfoDto> orders = orderService.getListOrderOfPersonByStatus(status, user.getId());
        return ResponseEntity.ok(orders);
    }
}