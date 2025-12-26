package com.example.staffmanagementsystem.service.profile;


import com.example.staffmanagementsystem.dto.profile.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.staffmanagementsystem.entity.NguoiDung;
import com.example.staffmanagementsystem.entity.NhanVien;
import com.example.staffmanagementsystem.repository.NguoiDungRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;

import com.example.staffmanagementsystem.service.AuditLogService;
import com.example.staffmanagementsystem.service.common.CurrentNhanVienService;
import com.example.staffmanagementsystem.utils.CurrentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final CurrentNhanVienService currentNhanVienService;
    private final NhanVienRepository nhanVienRepository;
    private final AuditLogService auditLogService;
    private final CurrentUserService currentUserService;
    private final NguoiDungRepository nguoiDungRepository;



    @Override
    public NhanVienTomTatDto getThongTinTomTatNhanVienHienTai() {
        Integer maNhanVien = currentNhanVienService.getMaNhanVien();

        return nhanVienRepository
                .findThongTinTomTatByMaNhanVien(maNhanVien)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy thông tin nhân viên: " + maNhanVien
                ));
    }

    @Override
    public ThongTinCaNhanDto getThongTinCaNhan() {

        // 1. Lấy MaNhanVien từ context hiện tại
        Integer maNhanVien = currentNhanVienService.getMaNhanVien();

        // 2. Query thông tin cá nhân theo MaNhanVien
        return nhanVienRepository.getThongTinCaNhan(maNhanVien);
    }

    @Override
    public ThongTinLienHeCongViecDto getThongTinLienHeCongViec() {

        // 1. Lấy MaNhanVien từ token
        Integer maNhanVien = currentNhanVienService.getMaNhanVien();

        // 2. Query thông tin liên hệ + công việc
        return nhanVienRepository.getThongTinLienHeCongViec(maNhanVien);
    }

    @Override
    public ThongTinTongQuanNhanVienDto getThongTinTongQuanNhanVien() {

        Integer maNhanVien = currentNhanVienService.getMaNhanVien();

        Object[] row = (Object[]) nhanVienRepository
                .getThongTinTongQuanNhanVien(maNhanVien);

        Timestamp ts = (Timestamp) row[3];

        return new ThongTinTongQuanNhanVienDto(
                (String) row[0],                         // HoTen
                (String) row[1],                         // AnhDaiDien
                (String) row[2],                         // TenViTri
                ts != null ? ts.toLocalDateTime() : null // DangNhapCuoi
        );
    }


    @Override
    @Transactional
    public ThongTinNhanVienFormDto capNhatThongTinCaNhan(
            CapNhatThongTinNhanVienDto dto
    ) {
        Integer maNhanVien = currentNhanVienService.getMaNhanVien();
        Integer maNguoiDung = currentUserService.getCurrentUserId();

        NhanVien nv = nhanVienRepository.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        List<String> fieldsUpdated = new ArrayList<>();

        if (dto.getHoTen() != null) {
            nv.setTenNhanVien(dto.getHoTen());
            fieldsUpdated.add("Họ tên");
        }

        if (dto.getAnhDaiDien() != null) {
            nv.setAnhDaiDien(dto.getAnhDaiDien());
            fieldsUpdated.add("Ảnh đại diện");
        }

        if (dto.getEmail() != null) {
            nv.setEmail(dto.getEmail());
            fieldsUpdated.add("Email");
        }

        if (dto.getNgaySinh() != null) {
            nv.setNgaySinh(dto.getNgaySinh());
            fieldsUpdated.add("Ngày sinh");
        }

        if (dto.getSdt() != null) {
            nv.setSdt(dto.getSdt());
            fieldsUpdated.add("SĐT");
        }

        if (dto.getGioiTinh() != null) {
            nv.setGioiTinh(dto.getGioiTinh());
            fieldsUpdated.add("Giới tính");
        }

        nhanVienRepository.save(nv);

        auditLogService.logUpdateProfile(
                maNguoiDung,
                maNhanVien,
                fieldsUpdated.isEmpty()
                        ? "Không có thay đổi"
                        : "Cập nhật thông tin cá nhân: " + String.join(", ", fieldsUpdated),
                "ThanhCong"
        );

        return nhanVienRepository.getThongTinForm(maNhanVien);
    }

    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{9,}$";


    @Override
    @Transactional
    public void doiMatKhau(DoiMatKhauDto dto) {

        Integer maNhanVien = currentNhanVienService.getMaNhanVien();
        Integer maNguoiDung = currentUserService.getCurrentUserId();

        NguoiDung nguoiDung = nguoiDungRepository.findById(maNguoiDung)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        try {
            if (!nguoiDung.getMatKhauHash().equals(dto.getMatKhauHienTai())) {
                throw new RuntimeException("Mật khẩu hiện tại không đúng");
            }

            if (!dto.getMatKhauMoi().equals(dto.getXacNhanMatKhau())) {
                throw new RuntimeException("Xác nhận mật khẩu không khớp");
            }

            if (!dto.getMatKhauMoi().matches(PASSWORD_REGEX)) {
                throw new RuntimeException(
                        "Mật khẩu phải ≥ 9 ký tự, gồm chữ hoa, chữ thường, số và ký tự đặc biệt"
                );
            }

            nguoiDung.setMatKhauHash(dto.getMatKhauMoi());
            nguoiDungRepository.save(nguoiDung);

            auditLogService.logUpdateProfile(
                    maNguoiDung,
                    maNhanVien,
                    "Đổi mật khẩu thành công",
                    "ThanhCong"
            );

        } catch (Exception ex) {

            auditLogService.logUpdateProfile(
                    maNguoiDung,
                    maNhanVien,
                    "Đổi mật khẩu thất bại: " + ex.getMessage(),
                    "ThatBai"
            );

            throw ex;
        }
    }
    @Override
    public ThongTinNhanVienFormDto getThongTinCaNhanForm() {

        Integer maNhanVien = currentNhanVienService.getMaNhanVien();

        return nhanVienRepository.getThongTinForm(maNhanVien);
    }

}

