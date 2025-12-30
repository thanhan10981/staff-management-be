package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.ApprovalDTO;
import com.example.staffmanagementsystem.repository.ApprovalRepository;
import com.example.staffmanagementsystem.repository.DonNghiPhepRepository;
import com.example.staffmanagementsystem.repository.YeuCauDoiCaRepository;
import com.example.staffmanagementsystem.service.ApprovalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final DonNghiPhepRepository donNghiPhepRepository;
    private final YeuCauDoiCaRepository yeuCauDoiCaRepository;

    @Override
    public List<ApprovalDTO> getPendingApprovals() {

        List<ApprovalDTO> result = new ArrayList<>();

        // 1️⃣ Nghỉ phép (JPQL → OK)
        result.addAll(
                approvalRepository.getPendingLeaveApprovals()
        );

        // 2️⃣ Đổi ca (native → Object[])
        List<Object[]> rows =
                yeuCauDoiCaRepository.getPendingShiftApprovals();

        for (Object[] r : rows) {
            result.add(new ApprovalDTO(
                    ((Number) r[0]).intValue(), // maNhanVien
                    (String) r[1],              // tenNhanVien
                    (String) r[2],              // loaiYeuCau
                    (String) r[3],              // thoiGian
                    (String) r[4],              // chiTiet
                    (String) r[5],              // lyDo
                    ((Number) r[6]).intValue()  // id
            ));
        }

        return result;
    }


    // ================= APPROVE =================
    @Override
    public void approve(String type, Integer id) {

        if ("NghiPhep".equals(type)) {
            donNghiPhepRepository.findById(id)
                    .ifPresent(d -> {
                        d.setTrangThai("Da duyet");
                        donNghiPhepRepository.save(d);
                    });
            return;
        }

        if ("DoiCa".equals(type)) {
            yeuCauDoiCaRepository.findById(id)
                    .ifPresent(y -> {
                        y.setTrangThai("Da duyet");
                        yeuCauDoiCaRepository.save(y);
                    });
        }
    }

    // ================= REJECT =================
    @Override
    public void reject(String type, Integer id) {

        if ("NghiPhep".equals(type)) {
            donNghiPhepRepository.findById(id)
                    .ifPresent(d -> {
                        d.setTrangThai("Tu choi");
                        donNghiPhepRepository.save(d);
                    });
            return;
        }

        if ("DoiCa".equals(type)) {
            yeuCauDoiCaRepository.findById(id)
                    .ifPresent(y -> {
                        y.setTrangThai("Tu choi");
                        yeuCauDoiCaRepository.save(y);
                    });
        }
    }
}
