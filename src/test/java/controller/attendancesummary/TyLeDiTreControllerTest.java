package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.TyLeDiTreController;
import com.example.staffmanagementsystem.dto.attendancesummary.LoaiThongKeNgayCong;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTrePhongBanChartResponse;
import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;
import com.example.staffmanagementsystem.service.ThongKeBaoCaoChamCong.TyLeDiTreService;
import com.example.staffmanagementsystem.utils.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TyLeDiTreController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class TyLeDiTreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TyLeDiTreService service;

    // ================== /ty-le-di-tre ==================
    @Test
    void tyLeDiTre_shouldReturnList() throws Exception {

        List<TyLeDiTreTheoPhongBanDTO> data = List.of(
                new TyLeDiTreTheoPhongBanDTOTestImpl("Phòng IT", 5L),
                new TyLeDiTreTheoPhongBanDTOTestImpl("Phòng HC", 2L)
        );

        Mockito.when(service.tyLeDiTre(
                Mockito.any(LocalDate.class),
                Mockito.any(LoaiThongKeNgayCong.class)
        )).thenReturn(data);

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/ty-le-di-tre")
                                .param("ngayChon", "2025-12-15")
                                .param("loai", "THANG_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenPhongBan").value("Phòng IT"))
                .andExpect(jsonPath("$[0].soLanDiTre").value(5))
                .andExpect(jsonPath("$[1].tenPhongBan").value("Phòng HC"))
                .andExpect(jsonPath("$[1].soLanDiTre").value(2));
    }

    // ================== /chart-phong-ban ==================
    @Test
    void chartTyLeDiTre_shouldReturnChartData() throws Exception {

        Mockito.when(service.tyLeDiTreChart(
                Mockito.any(LocalDate.class),
                Mockito.any(LoaiThongKeNgayCong.class)
        )).thenReturn(List.of(
                new TyLeDiTrePhongBanChartResponse("Phòng IT", 71.4),
                new TyLeDiTrePhongBanChartResponse("Phòng HC", 28.6)
        ));

        mockMvc.perform(
                        get("/api/cham-cong/thong-ke/chart-phong-ban")
                                .param("ngayChon", "2025-12-15")
                                .param("loai", "THANG_NAY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenPhongBan").value("Phòng IT"))
                .andExpect(jsonPath("$[0].tiLe").value(71.4))
                .andExpect(jsonPath("$[1].tenPhongBan").value("Phòng HC"))
                .andExpect(jsonPath("$[1].tiLe").value(28.6));
    }
}
