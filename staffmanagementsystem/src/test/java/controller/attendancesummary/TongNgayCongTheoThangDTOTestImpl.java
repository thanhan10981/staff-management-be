package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TongNgayCongTheoThangDTO;

public class TongNgayCongTheoThangDTOTestImpl
        implements TongNgayCongTheoThangDTO {

    private final Integer thang;
    private final Long tongNgayCong;

    public TongNgayCongTheoThangDTOTestImpl(
            Integer thang,
            Long tongNgayCong
    ) {
        this.thang = thang;
        this.tongNgayCong = tongNgayCong;
    }

    @Override
    public Integer getThang() {
        return thang;
    }

    @Override
    public Long getTongNgayCong() {
        return tongNgayCong;
    }
}
