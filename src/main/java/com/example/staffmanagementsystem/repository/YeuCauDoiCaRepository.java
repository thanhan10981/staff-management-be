package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.dto.ApprovalDTO;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaDetailView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaView;
import com.example.staffmanagementsystem.entity.LichTrucNgay;
import com.example.staffmanagementsystem.entity.YeuCauDoiCa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YeuCauDoiCaRepository
        extends JpaRepository<YeuCauDoiCa, Integer> {

    // ===============================
    //  VIEW TỔNG HỢP (native)
    // ===============================
    @Query(value = """
        SELECT 
            y.MaYeuCau       AS maYeuCau,
            y.NguoiGui       AS nguoiGui,
            nv1.HoTen        AS tenNguoiGui,
            y.NguoiNhan      AS nguoiNhan,
            nv2.HoTen        AS tenNguoiNhan,
            y.MaCa           AS maCa,
            clv.TenCa        AS tenCa,
            pb.TenPhongBan   AS tenPhongBan,
            y.NgayTruc       AS ngayTruc,
            y.LyDo           AS lyDo,
            y.TrangThai      AS trangThai
        FROM YeuCauDoiCa y
        JOIN NhanVien nv1 ON y.NguoiGui = nv1.MaNhanVien
        JOIN NhanVien nv2 ON y.NguoiNhan = nv2.MaNhanVien
        JOIN CaLamViec clv ON y.MaCa = clv.MaCa
        LEFT JOIN PhongBan pb ON nv1.MaPhongBan = pb.MaPhongBan
        """, nativeQuery = true)
    List<YeuCauDoiCaView> getAllView();

    // ===============================
    //  CA HIỆN TẠI CỦA NHÂN VIÊN
    // ===============================
    @Query("""
        SELECT lt
        FROM LichTrucNgay lt
        JOIN FETCH lt.caLamViec
        WHERE lt.nhanVien.maNhanVien = :maNhanVien
        ORDER BY lt.ngayTruc DESC
    """)
    List<LichTrucNgay> findCurrentShift(
            @Param("maNhanVien") Integer maNhanVien
    );

    // ===============================
    //  CHI TIẾT YÊU CẦU ĐỔI CA
    // ===============================
    @Query(value = """
        SELECT
            y.MaYeuCau        AS maYeuCau,
            y.NguoiGui        AS nguoiGui,
            nv1.HoTen         AS tenNguoiGui,
            y.NguoiNhan       AS nguoiNhan,
            nv2.HoTen         AS tenNguoiNhan,
            y.NgayTruc        AS ngayTruc,
            clv1.TenCa        AS tenCaHienTai,
            clv2.TenCa        AS tenCaMuonDoi,
            y.LyDo            AS lyDo,
            y.TrangThai       AS trangThai
        FROM YeuCauDoiCa y
        JOIN NhanVien nv1 ON y.NguoiGui = nv1.MaNhanVien
        JOIN NhanVien nv2 ON y.NguoiNhan = nv2.MaNhanVien
        JOIN CaLamViec clv2 ON y.MaCa = clv2.MaCa
        LEFT JOIN LichTrucNgay ltn 
            ON ltn.MaNhanVien = y.NguoiGui 
           AND ltn.NgayTruc = y.NgayTruc
        LEFT JOIN CaLamViec clv1 ON ltn.MaCa = clv1.MaCa
        WHERE y.MaYeuCau = :id
        """, nativeQuery = true)
    YeuCauDoiCaDetailView getDetail(@Param("id") Integer id);

    @Query(value = """
    SELECT 
        nv.MaNhanVien,
        nv.HoTen,
        'DoiCa',
        CAST(y.NgayTruc AS varchar),
        clv.TenCa,
        y.LyDo,
        y.MaYeuCau
    FROM YeuCauDoiCa y
    JOIN NhanVien nv ON y.NguoiGui = nv.MaNhanVien
    JOIN CaLamViec clv ON y.MaCa = clv.MaCa
    WHERE y.TrangThai = 'Cho duyet'
""", nativeQuery = true)
    List<Object[]> getPendingShiftApprovals();


}
