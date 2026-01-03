package controller;


import com.example.staffmanagementsystem.controller.TiemChungController;
import com.example.staffmanagementsystem.dto.TiemChungDTO;
import com.example.staffmanagementsystem.service.TiemChungService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = TiemChungController.class)
@WebMvcTest(TiemChungController.class)
@AutoConfigureMockMvc(addFilters = false)
class TiemChungControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TiemChungService tiemChungService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===== TC1: GET ALL =====
    @Test
    void getAll_ok() throws Exception {
        when(tiemChungService.getAll())
                .thenReturn(List.of(new TiemChungDTO(), new TiemChungDTO()));

        mockMvc.perform(get("/api/tiemchung"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== TC2: GET BY NHAN VIEN =====
    @Test
    void getByNhanVien_ok() throws Exception {
        when(tiemChungService.getByNhanVien(1))
                .thenReturn(List.of(new TiemChungDTO()));

        mockMvc.perform(get("/api/tiemchung/nhanvien/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // ===== TC3: CREATE =====
    @Test
    void create_ok() throws Exception {
        TiemChungDTO dto = TiemChungDTO.builder()
                .maNhanVien(1)
                .loai("COVID")
                .ngayTiem(LocalDate.of(2025, 1, 1))
                .ketQua("OK")
                .build();

        when(tiemChungService.create(any()))
                .thenReturn(dto);

        mockMvc.perform(post("/api/tiemchung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loai").value("COVID"));
    }
}
