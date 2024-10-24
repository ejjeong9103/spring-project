package com.estsoft.springproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;



public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter init 메소드");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Request URI
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println("FirstFilter doFilter 메소드의 request");
        System.out.println("requestURI: " + request.getRequestURI());

        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("FirstFilter doFilter 메소드의 response");
    }

    @Override
    public void destroy() {
        System.out.println("FirstFilter destroy 메소드");
    }
}
