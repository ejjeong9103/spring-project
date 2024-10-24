package com.estsoft.springproject.view.controller;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Person {

    private Long id;
    private String name;
    private int age;
    private List<String> hobbies;

}
