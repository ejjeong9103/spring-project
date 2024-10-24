package com.estsoft.springproject.user.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    // GET /login -> login 페이지로 연결
    @GetMapping("/login")
    public String login() {
        // WebSecurityConfiguration 에서의 form login
        // 인증 안된 사용자가 인증이 필요한 페이지에 접근 할 시
        return "login";
    }

    // GET /signup -> signup 페이지로 연결
    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
   @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
