package com.example.staffmanagementsystem.controller.auth;

import com.example.staffmanagementsystem.dto.auth.LoginRequest;
import com.example.staffmanagementsystem.dto.auth.LoginResponse;
import com.example.staffmanagementsystem.utils.JwtTokenUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final NguoiDungRepository nguoiDungRepo;


    public AuthController(AuthenticationManager authManager, JwtTokenUtil jwtUtil, UserDetailsService userDetailsService, NguoiDungRepository nguoiDungRepo) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.nguoiDungRepo = nguoiDungRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();

            // ⭐ Lấy thông tin user từ DB
            NguoiDung user = nguoiDungRepo.findByTenDangNhap(req.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // ⭐ Generate token kèm userId
            String token = jwtUtil.generateToken(user, userDetails);

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            return ResponseEntity.ok(new LoginResponse(token, roles));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }



}
