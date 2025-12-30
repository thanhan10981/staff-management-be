package controller.attendancesummary;

import com.example.staffmanagementsystem.dto.attendancesummary.TyLeDiTreTheoPhongBanDTO;

public class TyLeDiTreTheoPhongBanDTOTestImpl
        implements TyLeDiTreTheoPhongBanDTO {

    private final String tenPhongBan;
    private final Long soLanDiTre;

    public TyLeDiTreTheoPhongBanDTOTestImpl(
            String tenPhongBan,
            Long soLanDiTre
    ) {
        this.tenPhongBan = tenPhongBan;
        this.soLanDiTre = soLanDiTre;
    }

    @Override
    public String getTenPhongBan() {
        return tenPhongBan;
    }

    @Override
    public Long getSoLanDiTre() {
        return soLanDiTre;
    }
}
