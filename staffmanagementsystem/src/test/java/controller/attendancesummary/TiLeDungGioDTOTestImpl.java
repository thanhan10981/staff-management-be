package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TiLeDungGioDTO;

import java.time.LocalDate;

public class TiLeDungGioDTOTestImpl implements TiLeDungGioDTO {

    private final LocalDate tuNgay;
    private final LocalDate denNgay;
    private final Long soLanDungGio;
    private final Long tongSoLanDiLam;

    public TiLeDungGioDTOTestImpl(
            LocalDate tuNgay,
            LocalDate denNgay,
            Long soLanDungGio,
            Long tongSoLanDiLam
    ) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.soLanDungGio = soLanDungGio;
        this.tongSoLanDiLam = tongSoLanDiLam;
    }

    @Override
    public LocalDate getTuNgay() {
        return tuNgay;
    }

    @Override
    public LocalDate getDenNgay() {
        return denNgay;
    }

    @Override
    public Long getSoLanDungGio() {
        return soLanDungGio;
    }

    @Override
    public Long getTongSoLanDiLam() {
        return tongSoLanDiLam;
    }
}
