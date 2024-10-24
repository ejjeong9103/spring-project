package com.estsoft.springproject.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    @Column
    private String name;

    // 양방향 연관 관계 -> @OneToMany  (자주쓰지않는 형태, 성능이 안좋아짐)
    // 연관관계의 주인 명시
    @OneToMany(mappedBy = "team") // n쪽에 있는 FK를 들고있는 객체의 이름
    List<Members> members = new ArrayList<>();


}
