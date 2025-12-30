package controller.leaveoverview;

import com.example.staffmanagementsystem.controller.leaveoverview.LeaveStatsPanelController;
import com.example.staffmanagementsystem.dto.leaveoverview.*;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveStatsService;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveTotalStatisticService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LeaveStatsPanelController.class)
@AutoConfigureMockMvc(addFilters = false)

@ContextConfiguration(classes = LeaveStatsPanelController.class)
class LeaveStatsPanelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private LeaveTotalStatisticService totalStatisticService;

    @MockitoBean
    private LeaveStatsService leaveStatsService;

    @Test
    void getTotalAnnualLeave_shouldReturnValue() throws Exception {

        Mockito.when(totalStatisticService.getTotalLeaveStatistic(Mockito.any()))
                .thenReturn(
                        LeaveTotalStatisticDTO.builder()
                                .tongNgayNghiPhepNam(10)
                                .build()
                );

        LeaveTotalStatisticFilterRequest request =
                LeaveTotalStatisticFilterRequest.builder()
                        .timeRange(LoaiThongKeNgayCong.THANG_NAY)
                        .maPhongBan(1)
                        .build();

        mockMvc.perform(post("/api/leave-statistic/total-annual-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongNgayNghiPhepNam").value(10));
    }

    @Test
    void getTotalSickLeave_shouldReturnValue() throws Exception {

        Mockito.when(leaveStatsService.getTotalSickLeaveDays(Mockito.any()))
                .thenReturn(new TotalSickLeaveDaysDTO(5L));

        TotalSickLeaveFilterRequest request = new TotalSickLeaveFilterRequest();
        request.setTimeRange(LoaiThongKeNgayCong.THANG_NAY);

        mockMvc.perform(post("/api/leave-statistic/total-sick-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalSickLeaveDays").value(5));
    }
    @Test
    void getTotalUnpaidLeave_shouldReturnValue() throws Exception {

        Mockito.when(leaveStatsService.getTotalUnpaidLeaveDays(Mockito.any()))
                .thenReturn(
                        TotalUnpaidLeaveDaysDTO.builder()
                                .tongNgayNghiKhongLuong(3L)
                                .build()
                );

        LeaveUnpaidStatisticFilterRequest request =
                LeaveUnpaidStatisticFilterRequest.builder()
                        .timeRange(LoaiThongKeNgayCong.THANG_NAY)
                        .build();

        mockMvc.perform(post("/api/leave-statistic/unpaid-total")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tongNgayNghiKhongLuong").value(3));
    }
    @Test
    void getLeaveTypes_shouldReturnList() throws Exception {

        Mockito.when(leaveStatsService.getAllLeaveTypes())
                .thenReturn(
                        List.of(
                                new LeaveTypeDTO("Nghỉ phép năm"),
                                new LeaveTypeDTO("Nghỉ ốm")
                        )
                );

        mockMvc.perform(get("/api/leave-statistic/leave-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].loaiNghi").value("Nghỉ phép năm"));
    }
}
