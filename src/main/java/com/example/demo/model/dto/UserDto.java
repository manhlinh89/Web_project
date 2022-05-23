package com.example.demo.model.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;

    private String name;

    private String email;

    private String phone;

    private String address;

    //private Date birthday;
    private List<String> roles;
}
