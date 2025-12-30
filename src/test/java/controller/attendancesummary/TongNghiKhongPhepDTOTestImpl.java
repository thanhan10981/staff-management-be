package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNghiKhongPhepDTO;

import java.time.LocalDate;

public class TongNghiKhongPhepDTOTestImpl
        implements TongNghiKhongPhepDTO {

    private final LocalDate tuNgay;
    private final LocalDate denNgay;
    private final Long tongSoNghiKhongPhep;

    public TongNghiKhongPhepDTOTestImpl(
            LocalDate tuNgay,
            LocalDate denNgay,
            Long tongSoNghiKhongPhep
    ) {
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.tongSoNghiKhongPhep = tongSoNghiKhongPhep;
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
    public Long getTongSoNghiKhongPhep() {
        return tongSoNghiKhongPhep;
    }
}
