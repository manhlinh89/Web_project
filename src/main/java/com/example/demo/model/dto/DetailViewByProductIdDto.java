package com.example.demo.model.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class DetailViewByProductIdDto {
    private String product_id;
    private long count_view;
}
