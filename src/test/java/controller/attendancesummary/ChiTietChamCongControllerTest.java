package controller.attendancesummary;

import com.example.staffmanagementsystem.StaffmanagementsystemApplication;
import com.example.staffmanagementsystem.controller.attendancesummary.ChiTietChamCongController;
import com.example.staffmanagementsystem.dto.attendancesummary.ChiTietChamCongDTO;
import com.example.staffmanagementsystem.service.ChiTietChamCongService;
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
        controllers = ChiTietChamCongController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@ContextConfiguration(classes = StaffmanagementsystemApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class ChiTietChamCongControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChiTietChamCongService chiTietChamCongService;
    @Test
    void chiTietChamCong_shouldReturnList() throws Exception {

        ChiTietChamCongDTO dto =
                new ChiTietChamCongDTOTestImpl(
                        "Lê Văn Tùng",
                        "tung@hospital.vn"
                );

        Mockito.when(chiTietChamCongService.chiTietChamCong(
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class),
                Mockito.any(),
                Mockito.any()
        )).thenReturn(List.of(dto));

        mockMvc.perform(
                        get("/api/cham-cong/chi-tiet/ngay")
                                .param("tuNgay", "2025-12-01")
                                .param("denNgay", "2025-12-05")
                                .param("maPhongBan", "1")
                                .param("maViTri", "2")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tenNhanVien")
                        .value("Lê Văn Tùng"))
                .andExpect(jsonPath("$[0].email")
                        .value("tung@hospital.vn"));
    }


    @Test
    void chiTietChamCong_withoutOptionalFilters_shouldReturnList() throws Exception {

        Mockito.when(chiTietChamCongService.chiTietChamCong(
                Mockito.any(),
                Mockito.any(),
                Mockito.isNull(),
                Mockito.isNull()
        )).thenReturn(List.of());

        mockMvc.perform(
                        get("/api/cham-cong/chi-tiet/ngay")
                                .param("tuNgay", "2025-12-01")
                                .param("denNgay", "2025-12-05")
                )
                .andExpect(status().isOk());
    }
}
