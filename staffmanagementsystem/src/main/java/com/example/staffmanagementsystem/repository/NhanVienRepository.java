package com.example.staffmanagementsystem.repository;

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
   select pb.tenPhongBan, count(nv)
   from NhanVien nv join nv.phongBan pb
   group by pb.tenPhongBan
""")
    List<Object[]> countNhanVienByPhongBan();

    @Query("""
   select month(nv.ngayVaoLam), count(nv)
   from NhanVien nv
   group by month(nv.ngayVaoLam)
   order by month(nv.ngayVaoLam)
""")
    List<Object[]> countNhanVienByMonth();
    @Query("""
    SELECT COUNT(n)
    FROM NhanVien n
    WHERE YEAR(n.ngayVaoLam) < :year
       OR (YEAR(n.ngayVaoLam) = :year AND MONTH(n.ngayVaoLam) <= :month)
""")
    Long countNhanVienDenThang(
            @Param("month") int month,
            @Param("year") int year
    );

}

