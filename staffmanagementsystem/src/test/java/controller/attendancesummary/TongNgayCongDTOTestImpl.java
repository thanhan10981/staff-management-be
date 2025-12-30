package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongDTO;

import java.time.LocalDate;

public class TongNgayCongDTOTestImpl implements TongNgayCongDTO {

    private final LocalDate tuNgay;
    private final LocalDate denNgay;
    private final Integer tongSoNgayCong;

    public TongNgayCongDTOTestImpl(
            LocalDate tuNgay,
            LocalDate denNgay,
            Integer tongSoNgayCong
    ) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.tongSoNgayCong = tongSoNgayCong;
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
    public Integer getTongSoNgayCong() {
        return tongSoNgayCong;
    }
}
