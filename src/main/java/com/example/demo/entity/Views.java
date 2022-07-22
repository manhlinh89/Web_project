package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "view")
public class Views {
    @Id
    @Column(name = "product_id")
    private String product_id;

    @Column(name = "count_view")
    private long count_view;


}
