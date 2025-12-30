package controller.leaveoverview;

import com.example.staffmanagementsystem.controller.leaveoverview.LeaveReportExportController;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveExportRequest;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveReportExportPDFService;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveReportExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LeaveReportExportController.class)
@ContextConfiguration(classes = LeaveReportExportController.class)
class LeaveReportExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeaveReportExportService service;

    @MockitoBean
    private LeaveReportExportPDFService pdfService;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    void exportExcel_shouldReturnFile() throws Exception {


        Mockito.when(service.exportLeaveReport(Mockito.any()))
                .thenReturn(new byte[]{1, 2, 3});

        LeaveExportRequest request = new LeaveExportRequest();
        request.setTimeRange(LoaiThongKeNgayCong.THANG_NAY);
        request.setMaPhongBan(1);

        mockMvc.perform(post("/api/leave-report/exportExcel")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString("attachment")))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }

    @WithMockUser
    @Test
    void exportPDF_shouldReturnFile() throws Exception {


        Mockito.when(pdfService.exportLeaveReportPDF(Mockito.any()))
                .thenReturn(new byte[]{9, 9, 9});

        LeaveExportRequest request = new LeaveExportRequest();
        request.setTimeRange(LoaiThongKeNgayCong.THANG_NAY);

        mockMvc.perform(post("/api/leave-report/exportPDF")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition",
                        org.hamcrest.Matchers.containsString(".pdf")))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF));
    }
}
