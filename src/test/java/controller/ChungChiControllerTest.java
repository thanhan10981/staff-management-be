package controller;

import com.example.staffmanagementsystem.controller.ChungChiController;
import com.example.staffmanagementsystem.dto.ChungChiDTO;
import com.example.staffmanagementsystem.service.ChungChiService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = ChungChiController.class)
@WebMvcTest(ChungChiController.class)
@AutoConfigureMockMvc(addFilters = false)
class ChungChiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChungChiService chungChiService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===== TC1: GET ALL =====
    @Test
    void getAll_ok() throws Exception {
        when(chungChiService.getAll())
                .thenReturn(List.of(new ChungChiDTO(), new ChungChiDTO()));

        mockMvc.perform(get("/api/ChungChi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== TC2: GET BY NHAN VIEN =====
    @Test
    void getByNhanVien_ok() throws Exception {
        when(chungChiService.getByNhanVien(1))
                .thenReturn(List.of(new ChungChiDTO()));

        mockMvc.perform(get("/api/ChungChi/nhanvien/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // ===== TC3: CREATE =====
    @Test
    void create_ok() throws Exception {
        ChungChiDTO dto = ChungChiDTO.builder()
                .maNhanVien(1)
                .soChungChi("CC001")
                .ngayCap(LocalDate.of(2024, 1, 1))
                .trangThai("Còn hạn")
                .build();

        when(chungChiService.create(any())).thenReturn(dto);

        mockMvc.perform(post("/api/ChungChi")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soChungChi").value("CC001"));
    }

    // ===== TC4: UPDATE =====
    @Test
    void update_ok() throws Exception {
        ChungChiDTO dto = ChungChiDTO.builder()
                .soChungChi("CC002")
                .trangThai("Hết hạn")
                .build();

        when(chungChiService.update(eq(1), any()))
                .thenReturn(dto);

        mockMvc.perform(put("/api/ChungChi/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.soChungChi").value("CC002"));
    }

    // ===== TC5: DELETE =====
    @Test
    void delete_ok() throws Exception {
        doNothing().when(chungChiService).delete(1);

        mockMvc.perform(delete("/api/ChungChi/1"))
                .andExpect(status().isOk());
    }
}
