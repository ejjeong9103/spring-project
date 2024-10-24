package com.estsoft.springproject.domain;


import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;
}

