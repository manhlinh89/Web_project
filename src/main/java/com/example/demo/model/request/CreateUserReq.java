package com.example.demo.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserReq {
    /*@NotNull(message = "Full name is required")
    @NotEmpty(message = "Full name is required")
    private String name;

    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password is required")
    @Size(min = 4, max = 20, message = "Pasword must be between 4 and 20 characters")
    private String password;

    @Pattern(regexp="(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Please provide a valid phone number")
    private String phone;
     */
    @NotNull(message = "Họ tên trống")
    @NotEmpty(message = "Họ tên trống")
    @ApiModelProperty(
            example="Sam Smith",
            notes="Họ tên trống",
            required=true
    )
    @JsonProperty("full_name")
    private String fullName;

    @NotNull(message = "Email trống")
    @NotEmpty(message = "Email trống")
    @Email(message = "Email không đúng định dạng")
    @ApiModelProperty(
            example="sam.smith@gmail.com",
            notes="Email trống",
            required=true
    )
    private String email;

    @NotNull(message = "Mật khẩu trống")
    @NotEmpty(message = "Mật khẩu trống")
    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    @ApiModelProperty(
            example="verysecretpassword",
            notes="Mật khẩu trống",
            required=true
    )
    private String password;

    @Pattern(regexp="(09|01[2|6|8|9])+([0-9]{8})\\b", message = "Điện thoại không hợp lệ")
    @ApiModelProperty(
            example="0916016972",
            notes="Điện thoại trống",
            required=true
    )
    private String phone;

}
