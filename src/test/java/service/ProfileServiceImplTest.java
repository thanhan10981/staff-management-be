package service;

import com.example.staffmanagementsystem.dto.profile.CapNhatThongTinNhanVienDto;
import com.example.staffmanagementsystem.dto.profile.DoiMatKhauDto;
import com.example.staffmanagementsystem.dto.profile.NhanVienTomTatDto;
import com.example.staffmanagementsystem.dto.profile.ThongTinNhanVienFormDto;
import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.service.AuditLogService;
import com.example.staffmanagementsystem.service.common.CurrentNhanVienService;
import com.example.staffmanagementsystem.service.profile.ProfileServiceImpl;
import com.example.staffmanagementsystem.utils.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private CurrentNhanVienService currentNhanVienService;

    @Mock
    private NhanVienRepository nhanVienRepository;

    @Mock
    private AuditLogService auditLogService;

    @Mock
    private CurrentUserService currentUserService;

    @Mock
    private NguoiDungRepository nguoiDungRepository;

    // =============================
    // getThongTinTomTatNhanVienHienTai
    // =============================
    @Test
    void getThongTinTomTat_shouldReturnData() {
        when(currentNhanVienService.getMaNhanVien()).thenReturn(1);

        NhanVienTomTatDto dto =
                new NhanVienTomTatDto("Tùng", "tung@mail.com", "a.jpg", "Bác sĩ");

        when(nhanVienRepository.findThongTinTomTatByMaNhanVien(1))
                .thenReturn(Optional.of(dto));

        NhanVienTomTatDto result =
                profileService.getThongTinTomTatNhanVienHienTai();

        assertNotNull(result);
        assertEquals("Tùng", result.getTenNhanVien());
    }

    // =============================
    // capNhatThongTinCaNhan
    // =============================
    @Test
    void capNhatThongTinCaNhan_shouldUpdateAndLog() {
        when(currentNhanVienService.getMaNhanVien()).thenReturn(1);
        when(currentUserService.getCurrentUserId()).thenReturn(10);

        NhanVien nv = new NhanVien();
        when(nhanVienRepository.findById(1))
                .thenReturn(Optional.of(nv));

        ThongTinNhanVienFormDto formDto =
                ThongTinNhanVienFormDto.builder()
                        .maNhanVien(1)
                        .hoTen("Tùng mới")
                        .build();

        when(nhanVienRepository.getThongTinForm(1))
                .thenReturn(formDto);

        CapNhatThongTinNhanVienDto dto =
                CapNhatThongTinNhanVienDto.builder()
                        .hoTen("Tùng mới")
                        .email("new@mail.com")
                        .build();

        ThongTinNhanVienFormDto result =
                profileService.capNhatThongTinCaNhan(dto);

        assertEquals("Tùng mới", result.getHoTen());

        verify(auditLogService).logUpdateProfile(
                eq(10),
                eq(1),
                contains("Cập nhật thông tin cá nhân"),
                eq("ThanhCong")
        );
    }

    // =============================
    // doiMatKhau - success
    // =============================
    @Test
    void doiMatKhau_shouldSuccess() {
        when(currentNhanVienService.getMaNhanVien()).thenReturn(1);
        when(currentUserService.getCurrentUserId()).thenReturn(10);

        NguoiDung user = new NguoiDung();
        user.setMatKhauHash("Old@12345");

        when(nguoiDungRepository.findById(10))
                .thenReturn(Optional.of(user));

        DoiMatKhauDto dto = new DoiMatKhauDto();
        dto.setMatKhauHienTai("Old@12345");
        dto.setMatKhauMoi("New@123456");
        dto.setXacNhanMatKhau("New@123456");

        profileService.doiMatKhau(dto);

        verify(nguoiDungRepository).save(any());
        verify(auditLogService).logUpdateProfile(
                eq(10),
                eq(1),
                contains("Đổi mật khẩu thành công"),
                eq("ThanhCong")
        );
    }

    // =============================
    // doiMatKhau - sai mật khẩu
    // =============================
    @Test
    void doiMatKhau_shouldFail_whenWrongPassword() {
        when(currentNhanVienService.getMaNhanVien()).thenReturn(1);
        when(currentUserService.getCurrentUserId()).thenReturn(10);

        NguoiDung user = new NguoiDung();
        user.setMatKhauHash("Old@12345");

        when(nguoiDungRepository.findById(10))
                .thenReturn(Optional.of(user));

        DoiMatKhauDto dto = new DoiMatKhauDto();
        dto.setMatKhauHienTai("sai");
        dto.setMatKhauMoi("New@123456");
        dto.setXacNhanMatKhau("New@123456");

        assertThrows(
                RuntimeException.class,
                () -> profileService.doiMatKhau(dto)
        );

        verify(auditLogService).logUpdateProfile(
                eq(10),
                eq(1),
                contains("Đổi mật khẩu thất bại"),
                eq("ThatBai")
        );
    }
}
