package com.example.staffmanagementsystem.service.schedule;

import com.example.staffmanagementsystem.dto.schedule.DayDetailScheduleDTO;
import com.example.staffmanagementsystem.dto.schedule.MonthlyScheduleRowDTO;
import com.example.staffmanagementsystem.repository.LichTrucNgayRepository;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final LichTrucNgayRepository lichTrucNgayRepository;

    @Override
    public List<DayDetailScheduleDTO> getChiTietTheoNgayVaKhoa(
            LocalDate ngayTruc,
            Integer maKhoa
    ) {
        return lichTrucNgayRepository
                .findChiTietTheoNgayVaKhoa(ngayTruc, maKhoa);
    }

    @Override
    public byte[] exportMonthlyPdf(Integer maKhoa, int year, int month) {

        try {
            LocalDate from = LocalDate.of(year, month, 1);
            LocalDate to = from.withDayOfMonth(from.lengthOfMonth());

            List<MonthlyScheduleRowDTO> data =
                    lichTrucNgayRepository.getMonthlySchedule(maKhoa, from, to);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document doc = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(doc, out);

            doc.open();
            doc.add(new Paragraph(
                    "LỊCH TRỰC THÁNG " + month + "/" + year,
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)
            ));
            doc.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            Stream.of(
                    "Ngày", "Nhân viên", "Vị trí",
                    "Phòng", "Ca", "Giờ", "Trạng thái"
            ).forEach(h ->
                    table.addCell(new PdfPCell(new Phrase(h)))
            );

            for (MonthlyScheduleRowDTO r : data) {
                table.addCell(r.getNgayTruc().toString());
                table.addCell(r.getHoTen());
                table.addCell(r.getTenViTri());
                table.addCell(r.getTenPhong());
                table.addCell(r.getTenCa());
                table.addCell(r.getTongGioLam().toString());
                table.addCell(r.getTrangThai());
            }

            doc.add(table);
            doc.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi export PDF lịch trực", e);
        }
    }

}


