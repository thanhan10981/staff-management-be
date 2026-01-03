package controller;

import com.example.staffmanagementsystem.controller.KhoaController;
import com.example.staffmanagementsystem.entity.Khoa;
import com.example.staffmanagementsystem.service.KhoaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = KhoaController.class)
@WebMvcTest(KhoaController.class)
@AutoConfigureMockMvc(addFilters = false)
class KhoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KhoaService khoaService;

    // ===== TC1: GET ALL KHOA - OK =====
    @Test
    void getAllKhoa_ok() throws Exception {
        Khoa k1 = new Khoa();
        Khoa k2 = new Khoa();

        when(khoaService.getAll()).thenReturn(List.of(k1, k2));

        mockMvc.perform(get("/api/khoa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== TC2: GET ALL KHOA - EMPTY =====
    @Test
    void getAllKhoa_empty() throws Exception {
        when(khoaService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/khoa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
