package controller.schedule;

import com.example.staffmanagementsystem.controller.profile.ProfileController;
import com.example.staffmanagementsystem.controller.schedule.LichTrucController;
import com.example.staffmanagementsystem.dto.LichTrucNgayDTO;
import com.example.staffmanagementsystem.service.LichTrucService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ContextConfiguration(classes = LichTrucController.class)

@WebMvcTest(LichTrucController.class)
@AutoConfigureMockMvc(addFilters = false)
class LichTrucControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShiftService shiftService;

    @MockBean
    private LichTrucService lichTrucService;

    @Test
    void SMS96_getCountByMonth_ok() throws Exception {
        List<LichTrucNgayDTO> mockList =
                List.of(new LichTrucNgayDTO(), new LichTrucNgayDTO());

        when(shiftService.getLichTrucByKhoa(eq(1), any(), any()))
                .thenReturn(mockList);

        mockMvc.perform(get("/api/lichtruc/thang")
                        .param("maKhoa", "1")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    @Test
    void SMS97_assignSingle_ok() throws Exception {
        LichTrucNgayDTO dto = new LichTrucNgayDTO();
        dto.setMaNhanVien(1);

        when(shiftService.assignSingleShift(any()))
                .thenReturn(dto);

        mockMvc.perform(post("/api/lichtruc/single")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "maNhanVien":1,
                              "maCa":1,
                              "maPhong":1,
                              "ngayTruc":"2025-01-01"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maNhanVien").value(1));
    }

    @Test
    void SMS99_delete_ok() throws Exception {
        doNothing().when(shiftService).deleteShift(10);

        mockMvc.perform(delete("/api/lichtruc/10"))
                .andExpect(status().isOk());
    }

    @Test
    void SMS96_getEmployeeCountByMonth_uniqueEmployees() throws Exception {
        LichTrucNgayDTO d1 = new LichTrucNgayDTO();
        d1.setMaNhanVien(1);
        LichTrucNgayDTO d2 = new LichTrucNgayDTO();
        d2.setMaNhanVien(2);
        LichTrucNgayDTO d3 = new LichTrucNgayDTO();
        d3.setMaNhanVien(1); // trùng NV

        when(shiftService.getLichTrucByKhoa(eq(1), any(), any()))
                .thenReturn(List.of(d1, d2, d3));

        mockMvc.perform(get("/api/lichtruc/nhanvien/thang")
                        .param("maKhoa", "1")
                        .param("year", "2025")
                        .param("month", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEmployees").value(2));
    }

    @Test
    void SMS105_updateStatus_ok() throws Exception {
        LichTrucNgayDTO dto = new LichTrucNgayDTO();
        dto.setTrangThai("DONE");

        when(shiftService.updateShiftStatus(1, "DONE")).thenReturn(dto);

        mockMvc.perform(patch("/api/lichtruc/1/status")
                        .param("status", "DONE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trangThai").value("DONE"));
    }

    @Test
    void getDetailById_ok() throws Exception {
        when(shiftService.getShiftById(5))
                .thenReturn(new LichTrucNgayDTO());

        mockMvc.perform(get("/api/lichtruc/5"))
                .andExpect(status().isOk());
    }

}
