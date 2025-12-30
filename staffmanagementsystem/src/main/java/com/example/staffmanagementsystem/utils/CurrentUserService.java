package com.example.staffmanagementsystem.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final JwtTokenUtil jwtTokenUtil;
    private final HttpServletRequest request;

    public Integer getCurrentUserId() {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new RuntimeException("Thiếu token");

        String token = authHeader.substring(7);

        return jwtTokenUtil.getUserId(token);
    }
}
