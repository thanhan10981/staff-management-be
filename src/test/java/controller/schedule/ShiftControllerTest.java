package controller.schedule;



import com.example.staffmanagementsystem.controller.schedule.LichTrucController;
import com.example.staffmanagementsystem.controller.schedule.ShiftController;
import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.service.LichTrucService;
import com.example.staffmanagementsystem.service.schedule.ConfigService;
import com.example.staffmanagementsystem.service.schedule.ShiftService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = ShiftController.class)
@WebMvcTest(ShiftController.class)
@AutoConfigureMockMvc(addFilters = false)
class ShiftControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ShiftService shiftService;

    @MockBean
    ConfigService configService;

    @Test
    void SMS98_createPhanCong_ok() throws Exception {
        when(shiftService.createPhanCongAndGenerateLich(any()))
                .thenReturn(List.of(new LichTrucNgayDTO()));

        mockMvc.perform(post("/api/shifts/phancong")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "maNhanVien":1,
                          "maCa":1,
                          "maPhong":1,
                          "ngayBatDau":"2025-01-01",
                          "ngayKetThuc":"2025-01-03"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void SMS99_delete_notFound() throws Exception {
        doThrow(new IllegalArgumentException())
                .when(shiftService).deleteShift(99);

        mockMvc.perform(delete("/api/shifts/99"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void SMS97_assignSingle_badRequest() throws Exception {
        when(shiftService.assignSingleShift(any()))
                .thenThrow(new IllegalArgumentException("Lỗi"));

        mockMvc.perform(post("/api/shifts/assign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getShiftById_notFound() throws Exception {
        when(shiftService.getShiftById(99))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/shifts/detail/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateStatus_fail() throws Exception {
        when(shiftService.updateShiftStatus(1, "DONE"))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(patch("/api/shifts/1/status")
                        .param("status", "DONE"))
                .andExpect(status().isBadRequest());
    }


}
