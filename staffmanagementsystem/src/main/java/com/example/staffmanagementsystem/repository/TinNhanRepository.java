package com.example.staffmanagementsystem.repository;

import com.example.staffmanagementsystem.entity.TinNhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TinNhanRepository extends JpaRepository<TinNhan, Integer> {

    @Query("""
        SELECT t FROM TinNhan t
        WHERE (t.nguoiGui = :u1 AND t.nguoiNhan = :u2)
           OR (t.nguoiGui = :u2 AND t.nguoiNhan = :u1)
        ORDER BY t.thoiGianGui
    """)
    List<TinNhan> getLichSuChat(
            @Param("u1") Integer u1,
            @Param("u2") Integer u2
    );
}
