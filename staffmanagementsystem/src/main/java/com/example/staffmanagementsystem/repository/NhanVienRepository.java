package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.profile.*;
import com.example.staffmanagementsystem.dto.EmployeeOption;
import com.example.staffmanagementsystem.dto.QuyLuongPhongBanDto;
import com.example.staffmanagementsystem.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer> {


    // tìm theo khoa
    List<NhanVien> findByKhoa_Id(Integer maKhoa);

    // tìm theo khoa + phòng ban
    List<NhanVien> findByKhoa_IdAndPhongBan_Id(Integer maKhoa, Integer maPhongBan);


    @Query("""
        SELECT new com.example.staffmanagementsystem.dto.profile.NhanVienTomTatDto(
            nv.tenNhanVien,
            nv.email,
            nv.anhDaiDien,
            vt.tenViTri
        )
        FROM NhanVien nv
        JOIN nv.viTriCongViec vt
        WHERE nv.maNhanVien = :maNhanVien
    """)
    Optional<NhanVienTomTatDto> findThongTinTomTatByMaNhanVien(
            @Param("maNhanVien") Integer maNhanVien
    );

    @Query("""
        SELECT new com.example.staffmanagementsystem.dto.profile.ThongTinCaNhanDto(
            nv.tenNhanVien,
            nv.ngaySinh,
            nv.gioiTinh,
            nv.trinhDoChuyenMon
        )
        FROM NhanVien nv
        WHERE nv.maNhanVien = :maNhanVien
    """)
    ThongTinCaNhanDto getThongTinCaNhan(@Param("maNhanVien") Integer maNhanVien);

    @Query("""
    SELECT new com.example.staffmanagementsystem.dto.profile.ThongTinLienHeCongViecDto(
        nv.email,
        nv.sdt,
        nv.maNhanVien,
        pb.tenPhongBan
    )
    FROM NhanVien nv
    JOIN nv.phongBan pb
    WHERE nv.maNhanVien = :maNhanVien
""")
    ThongTinLienHeCongViecDto getThongTinLienHeCongViec(
            @Param("maNhanVien") Integer maNhanVien
    );

    @Query(value = """
    SELECT 
        nv.HoTen,
        nv.AnhDaiDien,
        vt.TenViTri,
        MAX(au.ThoiGian) AS DangNhapCuoi
    FROM NhanVien nv
    JOIN ViTriCongViec vt 
        ON vt.MaViTri = nv.MaViTri
    LEFT JOIN AuditLog au 
        ON au.MaNhanVien = nv.MaNhanVien
       AND au.HanhDong = 'LOGIN'
    WHERE nv.MaNhanVien = :maNhanVien
    GROUP BY 
        nv.HoTen,
        nv.AnhDaiDien,
        vt.TenViTri
""", nativeQuery = true)
    Object getThongTinTongQuanNhanVien(@Param("maNhanVien") Integer maNhanVien);

    @Query("""
    SELECT new com.example.staffmanagementsystem.dto.profile.ThongTinNhanVienFormDto(
        nv.maNhanVien,
        nv.tenNhanVien,
        nv.anhDaiDien,
        nv.email,
        nv.ngaySinh,
        nv.sdt,
        nv.gioiTinh,
        vt.tenViTri,
        pb.tenPhongBan
    )
    FROM NhanVien nv
    JOIN nv.viTriCongViec vt
    JOIN nv.phongBan pb
    WHERE nv.maNhanVien = :maNhanVien
""")
    ThongTinNhanVienFormDto getThongTinForm(
            @Param("maNhanVien") Integer maNhanVien
    );

    @Query("""
        SELECT 
            nv.maNhanVien AS maNhanVien,
            nv.tenNhanVien AS tenNhanVien
        FROM NhanVien nv
        WHERE nv.maNhanVien <> :maNhanVien
    """)
    List<EmployeeOption> findNhanVienMuonDoi(
            @Param("maNhanVien") Integer maNhanVien
    );

}

