package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongLanDiTreDTO;

import java.time.LocalDate;

public class TongLanDiTreDTOTestImpl implements TongLanDiTreDTO {

    private final LocalDate tuNgay;
    private final LocalDate denNgay;
    private final Long tongSoLanDiTre;

    public TongLanDiTreDTOTestImpl(
            LocalDate tuNgay,
            LocalDate denNgay,
            Long tongSoLanDiTre
    ) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.tongSoLanDiTre = tongSoLanDiTre;
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
    public Long getTongSoLanDiTre() {
        return tongSoLanDiTre;
    }
}
