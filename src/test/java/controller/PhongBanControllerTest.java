package controller;



import com.example.staffmanagementsystem.controller.PhongBanController;
import com.example.staffmanagementsystem.dto.PhongBanDTO;
import com.example.staffmanagementsystem.entity.PhongBan;
import com.example.staffmanagementsystem.service.impl.PhongBanService;
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

@ContextConfiguration(classes = PhongBanController.class)
@WebMvcTest(PhongBanController.class)
@AutoConfigureMockMvc(addFilters = false)
class PhongBanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhongBanService phongBanService;

    // ===== TC1: GET PHONG BAN THEO KHOA =====
    @Test
    void getPhongBanTheoKhoa_ok() throws Exception {
        when(phongBanService.getPhongBanTheoKhoa(1))
                .thenReturn(List.of(new PhongBanDTO(), new PhongBanDTO()));

        mockMvc.perform(get("/api/phongban/khoa/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ===== TC2: GET ALL PHONG BAN =====
    @Test
    void getAllPhongBan_ok() throws Exception {
        when(phongBanService.findAll())
                .thenReturn(List.of(new PhongBan(), new PhongBan()));

        mockMvc.perform(get("/api/phongban"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
