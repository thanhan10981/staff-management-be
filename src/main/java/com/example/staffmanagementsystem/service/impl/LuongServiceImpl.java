package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.CTLuongDTO;
import com.example.staffmanagementsystem.entity.LuongThang;
import com.example.staffmanagementsystem.repository.LuongThangRepository;
import com.example.staffmanagementsystem.repository.LuongPhuCapRepository;
import com.example.staffmanagementsystem.service.LuongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LuongServiceImpl implements LuongService {

    private final LuongThangRepository luongThangRepo;
    private final LuongPhuCapRepository luongPhuCapRepo;

    @Override
    public CTLuongDTO getSalaryOfNhanVien(Integer maNV) {

        var list = luongThangRepo.getLatestByNhanVien(maNV);
        if (list.isEmpty()) return null;

        LuongThang luongThang = list.get(0);

        var phuCapList = luongPhuCapRepo.findByLuongThang(luongThang.getMaLuong());

        CTLuongDTO dto = new CTLuongDTO();
        dto.setLuongCoBan(luongThang.getLuongCoBan());
        dto.setPhuCapTrucCa(luongThang.getPhuCapTrucCa());
        dto.setPhuCapKhac(luongThang.getPhuCapKhac());
        dto.setTongThuNhap(luongThang.getTongThuNhap());
        dto.setThueTncn(luongThang.getThueTncn());
        dto.setBhxh(luongThang.getBhxh());
        dto.setThucLanh(luongThang.getThucLanh());

        // map chi tiết phụ cấp
        phuCapList.forEach(pc -> {
            switch (pc.getLoaiPhuCap()) {
                case "PhauThuat":
                    dto.setPhuCapPhauThuat(pc.getSoTien());
                    break;

                case "DocHai":
                    dto.setPhuCapDocHai(pc.getSoTien());
                    break;

                case "AnCa":
                    dto.setPhuCapAnCa(pc.getSoTien());
                    break;

                case "LeTet":
                    dto.setPhuCapLeTet(pc.getSoTien());
                    break;

                case "TrucNgay":
                case "TrucDem":
                    // m đã có phuCapTrucCa tổng trong LuongThang
                    break;

                default:
                    dto.setPhuCapKhac(pc.getSoTien());
                    break;
            }
        });

        return dto;
    }
}
