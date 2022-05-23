package com.example.demo.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticDTO {
    private long sales;
    private long profit;
    private int quantity;
    private String createdAt;

    public StatisticDTO(long sales, long profit, int quantity,String createdAt){
        this.sales = sales;
        this.profit = profit;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }
}
