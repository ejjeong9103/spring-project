package com.estsoft.springproject.user.domain;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
public class Users implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users() {

    }

    // 여기부터 밑에는 UserDetails의 method
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 정보(Authorization, 인가) 관련 method
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        // 새싹 권한을 부여, role의 이름은 사용자가 지정// 새싹, 주니어,..., 관리자
    }

    @Override
    public String getUsername() {
        return email; // id는 내부 입력 pk값 이므로 email
    }

    @Override
    public boolean isAccountNonExpired() { // 해당 계정 만료상태  false = 만료

//        if (lastLoginDate.plusDays(1).isBefore(currentDate)) {
//            return false;  // 계정이 만료된 것으로 처리
//        } else {
//            return true;   // 계정이 만료되지 않은 것으로 처리
//        }  // 접속날짜가 하루 이상 지났다 -> false 이와같이 처리 가능
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 해당계정 잠김 = false
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
