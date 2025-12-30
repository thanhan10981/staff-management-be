package com.example.staffmanagementsystem.service.profile;


import com.example.staffmanagementsystem.dto.profile.*;

public interface ProfileService {

    NhanVienTomTatDto getThongTinTomTatNhanVienHienTai();
    ThongTinCaNhanDto getThongTinCaNhan();
    ThongTinLienHeCongViecDto getThongTinLienHeCongViec();
    ThongTinTongQuanNhanVienDto getThongTinTongQuanNhanVien();
    ThongTinNhanVienFormDto capNhatThongTinCaNhan(
            CapNhatThongTinNhanVienDto dto
    );
    void doiMatKhau(DoiMatKhauDto dto);
    ThongTinNhanVienFormDto getThongTinCaNhanForm();

}
