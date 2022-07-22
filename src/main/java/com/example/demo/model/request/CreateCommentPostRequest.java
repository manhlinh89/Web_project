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

public class CreateCommentPostRequest {
    private long postId;
    @NotBlank(message = "Nội dung trống")
    private String content;
}
