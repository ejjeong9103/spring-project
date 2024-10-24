package com.estsoft.springproject.example.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    String id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String author;

}
