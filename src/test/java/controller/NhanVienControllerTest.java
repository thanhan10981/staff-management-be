package controller;

import com.example.staffmanagementsystem.controller.KhoaController;
import com.example.staffmanagementsystem.controller.NhanVienController;
import com.example.staffmanagementsystem.dto.NhanVienDTO;
import com.example.staffmanagementsystem.service.NhanVienService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = NhanVienController.class)
@WebMvcTest(NhanVienController.class)
@AutoConfigureMockMvc(addFilters = false)
class NhanVienControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NhanVienService nhanVienService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===== TC1: GET ALL =====
    @Test
    void getAll_ok() throws Exception {
        when(nhanVienService.getAll())
                .thenReturn(List.of(new NhanVienDTO(), new NhanVienDTO()));

        mockMvc.perform(get("/api/NhanVien"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== TC2: GET BY ID =====
    @Test
    void getById_ok() throws Exception {
        NhanVienDTO dto = new NhanVienDTO();
        dto.setMaNhanVien(1);

        when(nhanVienService.getById(1)).thenReturn(dto);

        mockMvc.perform(get("/api/NhanVien/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maNhanVien").value(1));
    }

    // ===== TC3: CREATE =====
    @Test
    void create_ok() throws Exception {
        NhanVienDTO dto = new NhanVienDTO();
        dto.setTenNhanVien("Nguyen Van A");

        when(nhanVienService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/NhanVien")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenNhanVien").value("Nguyen Van A"));
    }

    // ===== TC4: UPDATE =====
    @Test
    void update_ok() throws Exception {
        NhanVienDTO dto = new NhanVienDTO();
        dto.setTenNhanVien("Updated Name");

        when(nhanVienService.update(eq(1), any())).thenReturn(dto);

        mockMvc.perform(put("/api/NhanVien/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenNhanVien").value("Updated Name"));
    }

    // ===== TC5: DELETE =====
    @Test
    void delete_ok() throws Exception {
        doNothing().when(nhanVienService).delete(1);

        mockMvc.perform(delete("/api/NhanVien/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted"));
    }
}