package com.estsoft.springproject.user.domain.dto;



import lombok.Getter;
import lombok.Setter;


// 회원 가입 요청시 controller에서 입력받는 정보 저장 DTO
@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
}
