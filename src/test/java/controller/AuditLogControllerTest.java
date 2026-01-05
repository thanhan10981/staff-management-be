package controller;

import com.example.staffmanagementsystem.controller.AuditLogController;
import com.example.staffmanagementsystem.dto.AuditLogDTO;
import com.example.staffmanagementsystem.dto.AuditLogResponseDTO;
import com.example.staffmanagementsystem.entity.AuditLog;
import com.example.staffmanagementsystem.service.AuditLogService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = AuditLogController.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuditLogController.class)
class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditLogService auditLogService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===============================
    // GET /api/AuditLog/nhanvien/{maNV}
    // ===============================
    @Test
    void getAuditLogs_byEmployee_success() throws Exception {

        Integer maNV = 1;

        List<AuditLogDTO> mockResult = List.of(
                new AuditLogDTO(
                        "LOGIN",
                        "User admin đăng nhập",
                        LocalDateTime.now(),
                        "Nguyễn Văn A",
                        "ThanhCong"
                )
        );

        Mockito.when(auditLogService.getLogsByEmployee(maNV))
                .thenReturn(mockResult);

        mockMvc.perform(get("/api/AuditLog/nhanvien/{maNV}", maNV))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].hanhDong").value("LOGIN"))
                .andExpect(jsonPath("$[0].trangThai").value("ThanhCong"))
                .andExpect(jsonPath("$[0].tenNguoiThucHien").value("Nguyễn Văn A"));
    }

    // ===============================
    // GET /api/AuditLog/recent
    // ===============================
    @Test
    void getRecentLogs_success() throws Exception {

        List<AuditLog> mockLogs = List.of(
                AuditLog.builder()
                        .maLog(1)
                        .hanhDong("UPDATE_PROFILE")
                        .trangThai("ThanhCong")
                        .thoiGian(LocalDateTime.now())
                        .build()
        );

        Mockito.when(auditLogService.getRecentActivities())
                .thenReturn(mockLogs);

        mockMvc.perform(get("/api/AuditLog/recent"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].hanhDong").value("UPDATE_PROFILE"))
                .andExpect(jsonPath("$[0].trangThai").value("ThanhCong"));
    }

    // ===============================
    // GET /api/AuditLog
    // ===============================
    @Test
    void getAllLogs_success() throws Exception {

        List<AuditLogResponseDTO> mockResult = List.of(
                new AuditLogResponseDTO(
                        1,
                        "admin",
                        "ADMIN",
                        LocalDateTime.now(),
                        "LOGIN",
                        "ThanhCong"
                )
        );

        Mockito.when(auditLogService.getAllLogs())
                .thenReturn(mockResult);

        mockMvc.perform(get("/api/AuditLog"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].maLog").value(1))
                .andExpect(jsonPath("$[0].tenDangNhap").value("admin"))
                .andExpect(jsonPath("$[0].vaiTro").value("ADMIN"))
                .andExpect(jsonPath("$[0].hanhDong").value("LOGIN"));
    }
}
