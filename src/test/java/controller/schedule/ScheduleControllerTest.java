package controller.schedule;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.staffmanagementsystem.controller.schedule.LichTrucController;
import com.example.staffmanagementsystem.controller.schedule.ScheduleController;
import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;
import com.example.staffmanagementsystem.service.schedule.ScheduleService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = ScheduleController.class)
@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
class ScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ScheduleService scheduleService;

    @Test
    void SMS105_getChiTietTheoNgay_ok() throws Exception {
        when(scheduleService.getChiTietTheoNgayVaKhoa(any(), eq(1)))
                .thenReturn(List.of(new DayDetailScheduleDTO()));

        mockMvc.perform(get("/api/schedules/chi-tiet")
                        .param("ngayTruc", "2025-01-01")
                        .param("maKhoa", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void getChiTiet_emptyList() throws Exception {
        when(scheduleService.getChiTietTheoNgayVaKhoa(any(), any()))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/schedules/chi-tiet")
                        .param("ngayTruc", "2025-01-01")
                        .param("maKhoa", "1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void getChiTiet_invalidDate_throwException() {
        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/schedules/chi-tiet")
                            .param("ngayTruc", "01-01-2025")
                            .param("maKhoa", "1"))
                    .andReturn();
        });
    }



}
