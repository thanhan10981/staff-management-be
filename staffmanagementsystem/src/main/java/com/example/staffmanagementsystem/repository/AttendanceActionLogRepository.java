package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.AttendanceActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceActionLogRepository extends JpaRepository<AttendanceActionLog, Long> {
}