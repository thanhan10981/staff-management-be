package controller.leaveoverview;

import com.example.staffmanagementsystem.controller.leaveoverview.LeaveOverviewController;
import com.example.staffmanagementsystem.dto.leaveoverview.LeaveOverviewDTO;
import com.example.staffmanagementsystem.service.leaveoverview.LeaveOverviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LeaveOverviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = LeaveOverviewControllerTest.TestApplication.class)
class LeaveOverviewControllerTest {

    /**
     * 🟢 Application giả để Spring Boot không bị lạc package
     */
    @SpringBootConfiguration
    @Import(LeaveOverviewController.class)
    static class TestApplication {
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LeaveOverviewService leaveOverviewService;

    @Test
    void getLeaveOverview_shouldReturnList() throws Exception {

        Mockito.when(leaveOverviewService.getLeaveOverview(Mockito.any()))
                .thenReturn(List.of(
                        LeaveOverviewDTO.builder()
                                .avatar("nv01.png")
                                .tenNhanVien("Lê Văn Tùng")
                                .email("tung@hospital.vn")
                                .tenPhongBan("Khoa Nội")
                                .tongNghiPhepNam(3)
                                .tongNghiBenh(1)
                                .nghiKhongLuongVuot(0)
                                .tongNgayNghi(4)
                                .soNgayConLai(8)
                                .build()
                ));

        mockMvc.perform(
                        post("/api/leave-overview")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                      "timeRange": "THANG_NAY",
                                      "maPhongBan": 1,
                                      "tenPhongBan": null
                                    }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenNhanVien").value("Lê Văn Tùng"))
                .andExpect(jsonPath("$[0].email").value("tung@hospital.vn"))
                .andExpect(jsonPath("$[0].tenPhongBan").value("Khoa Nội"))
                .andExpect(jsonPath("$[0].tongNgayNghi").value(4))
                .andExpect(jsonPath("$[0].soNgayConLai").value(8));
    }
}
