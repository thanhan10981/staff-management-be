package controller;

import com.example.staffmanagementsystem.controller.AdminUserController;
import com.example.staffmanagementsystem.controller.AuditLogController;
import com.example.staffmanagementsystem.dto.CreateUserRequest;
import com.example.staffmanagementsystem.dto.UpdateUserRequest;
import com.example.staffmanagementsystem.dto.UserResponseDTO;
import com.example.staffmanagementsystem.service.NguoiDungService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = AdminUserController.class)
@AutoConfigureMockMvc(addFilters = false)

@WebMvcTest(AdminUserController.class)
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NguoiDungService nguoiDungService;

    @Autowired
    private ObjectMapper objectMapper;

    // =========================
    // GET /api/admin/users
    // =========================
    @Test
    void getAllUsers_success() throws Exception {

        UserResponseDTO user = new UserResponseDTO();
        user.setMaNguoiDung(1);
        user.setTenDangNhap("admin");
        user.setVaiTro("ADMIN");
        user.setTrangThai("HoatDong");

        Mockito.when(nguoiDungService.getAllUsers())
                .thenReturn(List.of(user));

        mockMvc.perform(get("/api/admin/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].maNguoiDung").value(1))
                .andExpect(jsonPath("$[0].tenDangNhap").value("admin"))
                .andExpect(jsonPath("$[0].vaiTro").value("ADMIN"));
    }

    // =========================
    // DELETE /api/admin/users/{id}
    // =========================
    @Test
    void deleteOne_success() throws Exception {

        Integer id = 1;
        Mockito.doNothing().when(nguoiDungService).deleteOne(id);

        mockMvc.perform(delete("/api/admin/users/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(nguoiDungService).deleteOne(id);
    }

    // =========================
    // POST /api/admin/users/delete-many
    // =========================
    @Test
    void deleteMany_success() throws Exception {

        List<Integer> ids = List.of(1, 2, 3);
        Mockito.doNothing().when(nguoiDungService).deleteMany(ids);

        mockMvc.perform(post("/api/admin/users/delete-many")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());

        Mockito.verify(nguoiDungService).deleteMany(ids);
    }

    // =========================
    // POST /api/admin/users
    // =========================
    @Test
    void createUser_success() throws Exception {

        CreateUserRequest request = new CreateUserRequest();
        request.setTenDangNhap("user1");
        request.setMatKhau("123456");
        request.setVaiTro("USER");
        request.setMaNhanVien(10);
        request.setPermissionIds(List.of(1, 2));

        Mockito.doNothing().when(nguoiDungService).createUser(Mockito.any());

        mockMvc.perform(post("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(nguoiDungService).createUser(Mockito.any(CreateUserRequest.class));
    }

    // =========================
    // PUT /api/admin/users/{id}
    // =========================
    @Test
    void updateUser_success() throws Exception {

        Integer id = 1;
        UpdateUserRequest request = new UpdateUserRequest();
        request.setTenDangNhap("user_update");
        request.setVaiTro("ADMIN");
        request.setMaNhanVien(20);
        request.setPermissionIds(List.of(3, 4));

        Mockito.doNothing().when(nguoiDungService).updateUser(Mockito.eq(id), Mockito.any());

        mockMvc.perform(put("/api/admin/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Mockito.verify(nguoiDungService).updateUser(Mockito.eq(id), Mockito.any(UpdateUserRequest.class));
    }
}
