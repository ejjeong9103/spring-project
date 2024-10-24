package com.estsoft.springproject.domain;

import jakarta.persistence.*;

@Entity
public class Locker {
    @Id
    @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    @Column
    private String name;

    @OneToOne(mappedBy = "locker") // 연관관계의 주체가 아님을 표기하는 mappedBy
    private Members members;   // 1:1 양방향 연관관계 설정 -> 권장하지 않음
}
