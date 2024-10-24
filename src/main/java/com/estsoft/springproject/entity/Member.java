package com.estsoft.springproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table (name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;        // DB 테이블의 id와 컬럼 매칭

    @Column(name = "name", nullable = false)
    private String name;    // DB 테이블의 name과 컬럼 매칭
}