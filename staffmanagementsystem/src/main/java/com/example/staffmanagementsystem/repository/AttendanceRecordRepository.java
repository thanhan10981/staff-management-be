package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {  // ← Đổi Long → Integer cho @Id

    // Sửa: dùng maNV (String) thay vì nhanVien.maNhanVien
    @Query("SELECT r FROM AttendanceRecord r WHERE r.maNV = :employeeIdStr AND CAST(r.checkInTime AS date) = :workDate")
    Optional<AttendanceRecord> findByEmployeeIdAndWorkDate(@Param("employeeIdStr") String employeeIdStr,
                                                           @Param("workDate") LocalDate workDate);

    // Sửa: dùng maNV
    @Query("SELECT r FROM AttendanceRecord r WHERE r.maNV = :employeeIdStr ORDER BY r.checkInTime DESC")
    List<AttendanceRecord> findByEmployeeIdOrderByWorkDateDesc(@Param("employeeIdStr") String employeeIdStr);

    // Sửa method filter: dùng maNV và cast employeeId (Long) sang String
    @Query("""
SELECT r FROM AttendanceRecord r
WHERE CAST(r.checkInTime AS date) BETWEEN :from AND :to
AND (:employeeIdStr IS NULL OR r.maNV = :employeeIdStr)
ORDER BY r.checkInTime ASC
""")
    List<AttendanceRecord> filter(@Param("from") LocalDate from,
                                  @Param("to") LocalDate to,
                                  @Param("employeeIdStr") String employeeIdStr);

}