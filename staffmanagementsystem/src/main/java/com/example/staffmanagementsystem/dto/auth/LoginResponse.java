package com.example.staffmanagementsystem.dto.auth;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private List<String> roles;

    public LoginResponse(String token, List<String> roles) {
        this.token = token;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
