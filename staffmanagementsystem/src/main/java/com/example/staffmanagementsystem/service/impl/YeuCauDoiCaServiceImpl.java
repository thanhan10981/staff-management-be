package com.example.staffmanagementsystem.service.impl;

import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateDTO;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaCreateView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaDetailView;
import com.example.staffmanagementsystem.dto.YeuCauDoiCaView;
import com.example.staffmanagementsystem.entity.YeuCauDoiCa;
import com.example.staffmanagementsystem.repository.CaLamViecRepository;
import com.example.staffmanagementsystem.repository.NhanVienRepository;
import com.example.staffmanagementsystem.repository.YeuCauDoiCaRepository;
import com.example.staffmanagementsystem.service.YeuCauDoiCaService;
import org.springframework.stereotype.Service;
import com.example.staffmanagementsystem.entity.LichTrucNgay;


import java.util.List;

@Service
public class YeuCauDoiCaServiceImpl implements YeuCauDoiCaService {

    private final YeuCauDoiCaRepository repository;
    private final NhanVienRepository nhanVienRepository;
    private final CaLamViecRepository  caLamViecRepository;

    public YeuCauDoiCaServiceImpl(YeuCauDoiCaRepository repository, NhanVienRepository nhanVienRepository, CaLamViecRepository caLamViecRepository) {
        this.repository = repository;
        this.nhanVienRepository = nhanVienRepository;
        this.caLamViecRepository = caLamViecRepository;
    }

    @Override
    public List<YeuCauDoiCaView> getAll() {
        return repository.getAllView();
    }

    @Override
    public YeuCauDoiCaCreateView getCreateForm(Integer nhanVienId) {

        YeuCauDoiCaCreateView view = new YeuCauDoiCaCreateView();

        // ======================
        // 1. CA HIỆN TẠI
        // ======================
        List<LichTrucNgay> list = repository.findCurrentShift(nhanVienId);
        if (!list.isEmpty()) {
            LichTrucNgay lich = list.get(0);

            view.setMaCaHienTai(lich.getMaCa());
            view.setNgayTruc(lich.getNgayTruc());

            if (lich.getCaLamViec() != null) {
                view.setTenCaHienTai(lich.getCaLamViec().getTenCa());
            }

        }

        // ======================
        // 2. NHÂN VIÊN MUỐN ĐỔI
        // ======================
        view.setNhanVienOptions(
                nhanVienRepository.findNhanVienMuonDoi(nhanVienId)
        );

        // ======================
        // 3. CA MUỐN ĐỔI
        // ======================
        view.setCaOptions(
                caLamViecRepository.findCaMuonDoi()
        );

        return view;
    }



    @Override
    public void create(YeuCauDoiCaCreateDTO dto) {

        // ⚠️ TẠM THỜI – sau này lấy từ SecurityContext
        Integer nguoiGuiId = 1;

        YeuCauDoiCa entity = new YeuCauDoiCa();
        entity.setNguoiGui(nguoiGuiId);
        entity.setNguoiNhan(dto.getNguoiNhan());
        entity.setMaCa(dto.getMaCa());
        entity.setNgayTruc(dto.getNgayTruc());
        entity.setLyDo(dto.getLyDo());
        entity.setTrangThai("Cho duyet");

        repository.save(entity);
    }


    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public YeuCauDoiCaDetailView getDetail(Integer id) {
        return repository.getDetail(id);
    }

}
