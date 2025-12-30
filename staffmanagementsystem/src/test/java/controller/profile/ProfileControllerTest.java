package controller.profile;

import com.example.staffmanagementsystem.controller.profile.ProfileController;
import com.example.staffmanagementsystem.dto.profile.*;
import com.example.staffmanagementsystem.service.profile.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@ContextConfiguration(classes = ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean   // ✅ THAY THẾ @MockBean (Spring Boot 3.4+)
    private ProfileService profileService;

    // ===================== GET /summary =====================
    @Test
    void getThongTinTomTat_shouldReturn200() throws Exception {
        when(profileService.getThongTinTomTatNhanVienHienTai())
                .thenReturn(new NhanVienTomTatDto(
                        "Tùng",
                        "mail@test.com",
                        "a.jpg",
                        "Bác sĩ"
                ));

        mockMvc.perform(get("/api/profile/summary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tenNhanVien").value("Tùng"))
                .andExpect(jsonPath("$.email").value("mail@test.com"))
                .andExpect(jsonPath("$.tenViTri").value("Bác sĩ"));

    }

    // ===================== GET /me =====================
    @Test
    void getThongTinCaNhan_shouldReturn200() throws Exception {
        ThongTinCaNhanDto dto = new ThongTinCaNhanDto();
        dto.setHoTen("Nguyễn Văn A");

        when(profileService.getThongTinCaNhan())
                .thenReturn(dto);

        mockMvc.perform(get("/api/profile/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hoTen").value("Nguyễn Văn A"));
    }

    // ===================== GET /lien-he-cong-viec =====================
    @Test
    void getThongTinLienHeCongViec_shouldReturn200() throws Exception {
        ThongTinLienHeCongViecDto dto = new ThongTinLienHeCongViecDto();
        dto.setEmail("work@mail.com");

        when(profileService.getThongTinLienHeCongViec())
                .thenReturn(dto);

        mockMvc.perform(get("/api/profile/lien-he-cong-viec"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("work@mail.com"));
    }


    // ===================== GET /me/overview =====================
    @Test
    void getThongTinTongQuan_shouldReturn200() throws Exception {
        ThongTinTongQuanNhanVienDto dto =
                new ThongTinTongQuanNhanVienDto(
                        "Nguyễn Văn A",
                        "a.jpg",
                        "Bác sĩ",
                        null
                );

        when(profileService.getThongTinTongQuanNhanVien())
                .thenReturn(dto);

        mockMvc.perform(get("/api/profile/me/overview"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hoTen").value("Nguyễn Văn A"))
                .andExpect(jsonPath("$.tenViTri").value("Bác sĩ"));
    }

    // ===================== PUT /thong-tin-ca-nhan =====================
    @Test
    void capNhatThongTinCaNhan_shouldReturn200() throws Exception {
        ThongTinNhanVienFormDto dto = new ThongTinNhanVienFormDto();
        dto.setHoTen("Tên mới");

        when(profileService.capNhatThongTinCaNhan(any()))
                .thenReturn(dto);

        mockMvc.perform(
                        put("/api/profile/thong-tin-ca-nhan")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                      "hoTen": "Tên mới"
                                    }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hoTen").value("Tên mới"));
    }

    // ===================== GET /me/form =====================
    @Test
    void getThongTinCaNhanForm_shouldReturn200() throws Exception {
        ThongTinNhanVienFormDto dto = new ThongTinNhanVienFormDto();
        dto.setEmail("abc@mail.com");

        when(profileService.getThongTinCaNhanForm())
                .thenReturn(dto);

        mockMvc.perform(get("/api/profile/me/form"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("abc@mail.com"));
    }

    // ===================== PUT /doi-mat-khau =====================
    @Test
    void doiMatKhau_shouldReturnMessage() throws Exception {
        doNothing().when(profileService).doiMatKhau(any());

        mockMvc.perform(
                        put("/api/profile/doi-mat-khau")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                      "matKhauHienTai": "Old@12345",
                                      "matKhauMoi": "New@123456",
                                      "xacNhanMatKhau": "New@123456"
                                    }
                                """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Đổi mật khẩu thành công"));
    }
}
