package com.example.staffmanagementsystem.controller;

import com.example.staffmanagementsystem.service.AuditLogService;
import com.example.staffmanagementsystem.utils.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // 🔥 TẮT SECURITY
class AuditLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuditLogService auditLogService;

    // 👉 MOCK JWT BEAN ĐỂ CONTEXT LOAD ĐƯỢC
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void getAllLogs_shouldReturn200() throws Exception {
        when(auditLogService.getAllLogs()).thenReturn(List.of());

        mockMvc.perform(get("/api/AuditLog"))
                .andExpect(status().isOk());
    }

    @Test
    void getRecentLogs_shouldReturn200() throws Exception {
        when(auditLogService.getRecentActivities()).thenReturn(List.of());

        mockMvc.perform(get("/api/AuditLog/recent"))
                .andExpect(status().isOk());
    }
}
