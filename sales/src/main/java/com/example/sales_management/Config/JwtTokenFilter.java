package com.example.sales_management.Config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public JwtTokenFilter(JwtDecoder jwtDecoder, JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Lấy token từ cookie có tên "Authorization"
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Nếu token hợp lệ, tiến hành xác thực người dùng
        if (token != null) {
            try {
                Jwt jwt = jwtDecoder.decode(token); // Giải mã và kiểm tra tính hợp lệ của JWT
                Authentication authentication = jwtAuthenticationConverter.convert(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication); // Đặt Authentication vào SecurityContext
            } catch (Exception e) {
                // Xử lý trường hợp token không hợp lệ
                SecurityContextHolder.clearContext(); // Xoá thông tin xác thực nếu token không hợp lệ
            }
        }

        filterChain.doFilter(request, response); // Tiến hành tiếp tục chuỗi lọc
    }
}
