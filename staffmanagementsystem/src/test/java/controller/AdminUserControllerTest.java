package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.NguoiDungService;
import com.example.staffmanagementsystem.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // 🔥 TẮT SECURITY
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NguoiDungService nguoiDungService;

    // 🔥 MOCK JWT ĐỂ CONTEXT LOAD ĐƯỢC
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void getAllUsers_shouldReturn200() throws Exception {
        when(nguoiDungService.getAllUsers()).thenReturn(List.of());

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk());
    }

    @Test
    void createUser_shouldReturn200() throws Exception {
        String json = """
            {
              "tenDangNhap": "test",
              "matKhau": "123",
              "vaiTro": "Admin",
              "maNhanVien": 1,
              "permissionIds": [1,2]
            }
        """;

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser_shouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/admin/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser_shouldReturn200() throws Exception {
        String json = """
        {
          "tenDangNhap": "updatedUser",
          "vaiTro": "Admin",
          "maNhanVien": 2,
          "permissionIds": [1,3]
        }
    """;

        mockMvc.perform(put("/api/admin/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void deleteManyUsers_shouldReturn200() throws Exception {
        String json = """
        [1, 2, 3]
    """;

        mockMvc.perform(post("/api/admin/users/delete-many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}
