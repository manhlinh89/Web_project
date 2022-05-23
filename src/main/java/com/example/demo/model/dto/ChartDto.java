package com.example.demo.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartDto {
    private String label;
    private int value;
    public ChartDto(String label, int value) {
        this.label = label;
        this.value = value;
    }
}
