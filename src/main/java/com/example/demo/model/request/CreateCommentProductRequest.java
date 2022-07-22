package com.example.demo.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCommentProductRequest {
    private String productId;
    @NotBlank(message = "Nội dung bình luận trống!")
    private String content;
}
