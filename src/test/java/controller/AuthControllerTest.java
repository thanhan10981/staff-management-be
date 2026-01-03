package controller;

import com.example.staffmanagementsystem.controller.AuditLogController;
import com.example.staffmanagementsystem.controller.auth.AuthController;
import com.example.staffmanagementsystem.dto.auth.LoginRequest;
import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.service.AuditLogService;
import com.example.staffmanagementsystem.utils.JwtTokenUtil;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private NguoiDungRepository nguoiDungRepository;

    @MockBean
    private AuditLogService auditLogService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===============================
    // SMS101 - Login success
    // ===============================
    @Test
    void login_success_returnTokenAndRoles() throws Exception {

        LoginRequest request = new LoginRequest("admin", "123456");

        User principal = new User(
                "admin",
                "123456",
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        principal.getAuthorities()
                );

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(authentication);

        NguoiDung user = new NguoiDung();
        user.setMaNguoiDung(1);
        user.setMaNhanVien(10);
        user.setTenDangNhap("admin");

        Mockito.when(nguoiDungRepository.findByTenDangNhap("admin"))
                .thenReturn(Optional.of(user));

        Mockito.when(jwtTokenUtil.generateToken(Mockito.eq(user), Mockito.any()))
                .thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_ADMIN"));

        Mockito.verify(auditLogService)
                .logLogin(1, 10, "admin");
    }

    // ===============================
    // SMS101 - Login fail
    // ===============================
    @Test
    void login_fail_invalidCredentials() throws Exception {

        LoginRequest request = new LoginRequest("admin", "wrong");

        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Invalid credentials"));

        Mockito.verify(auditLogService)
                .logLoginFail("admin");
    }
}
