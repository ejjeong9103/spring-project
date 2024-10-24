package com.estsoft.springproject.user.config;


import com.estsoft.springproject.user.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


// Spring Security 설정
@Configuration
public class WebSecurityConfiguration {

    private final UserDetailService userDetailService;

    public WebSecurityConfiguration(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // ignore 처리
    @Bean
    public WebSecurityCustomizer ignore() {
        return webSecurity -> webSecurity.ignoring()
//                .requestMatchers(toH2Console()) // h2-console에서 mysql로 db옮겼으니 주석처리함
                // h2-console은 개발환경에서만 쓰기때문에 불필요한 security를 타지않음
                .requestMatchers("/static/**", "/swagger-ui/**","/v3/api-docs/**","swagger-ui/html");

    }

    // filterchain -> 특정 요청에 대한 보안 구성
    // 6.1 버전 기준으로 바뀜 and() 없어지고 람다표현식으로 바뀜
    // 주석처리는 과거표현식

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                        custom -> custom.requestMatchers("/login", "/signup", "/user").permitAll() // 로그인, 회원가입, 유저 쪽에 접근시 모두 허가
//                                .requestMatchers("/articles/**").hasRole("ADMIN") // ROLE_ADMIN 권한이 있어야 /articles/**에 들어갈 수 있음(ADMIN 아니고 ROLE_ADMIN)
                                .anyRequest().authenticated() // 앞부분을 제외하고 전부 인증이 필요하다는 설정
//                                .anyRequest().permitAll() // 실습을 위한 임시 전체 허용
                        // 3) 인증, 인가 설정
                )
//                .requestMatchers("/login", "/signup", "/user").permitAll()
//                .requestMatchers("/api/external").hasRole("admin") // admin 일때만 인가처리
//                .anyRequest().authenticated()
                .formLogin(custom -> custom.loginPage("/login") //4) 폼 기반 로그인 설정
                        .defaultSuccessUrl("/articles",true) // 로그인 성공했을 경우 리다이렉션 URL
                )
//                .loginPage("/login")
//                .defaultSuccessUrl("/articles")
                .logout(custom -> custom // 5) 로그아웃 설정
                        .logoutSuccessUrl("/login") // 로그인 성공했을 경우 리다이렉션
                        .invalidateHttpSession(true) // 로그아웃 했을 시 해당 세션 종료
                )
//                .logoutSuccessUrl("/login")
//                .invalidateHttpSession(true) // 로그아웃 했을 시 해당 세션 종료
                .csrf(custom -> custom.disable()) // 6) csrf 비활성화(default -> 활성)
                .build();
//                .disable()
//                .build();
    }

    // 7) 인증관리자 관련 설정  => 삭제 가능!
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) {
//        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailService)  // 8) 사용자 정보 서비스 설정
//                .passwordEncoder(bCryptPasswordEncoder) // 패스워드 인코더로 사용할 빈
//                .build();
//    }


    // 패스워드 암호화 방식 정의
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
