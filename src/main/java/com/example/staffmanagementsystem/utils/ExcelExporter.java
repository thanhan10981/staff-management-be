package com.example.staffmanagementsystem.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelExporter {

    public static byte[] export(List<?> data) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Salary Report");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Nhân viên");
            header.createCell(1).setCellValue("Email");
            header.createCell(2).setCellValue("Phòng ban");
            header.createCell(3).setCellValue("Lương cơ bản");
            header.createCell(4).setCellValue("Phụ cấp");
            header.createCell(5).setCellValue("OT");
            header.createCell(6).setCellValue("Tổng lương");

            int rowIdx = 1;
            for (Object obj : data) {
                var dto = (com.example.staffmanagementsystem.dto.SalaryDetailDto) obj;
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(dto.getTenNhanVien());
                row.createCell(1).setCellValue(dto.getEmail());
                row.createCell(2).setCellValue(dto.getPhongBan());
                row.createCell(3).setCellValue(dto.getLuongCoBan());
                row.createCell(4).setCellValue(dto.getPhuCap());
                row.createCell(5).setCellValue(dto.getOt());
                row.createCell(6).setCellValue(dto.getTongLuong());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
        catch (Exception ex) {
            throw new RuntimeException("Lỗi xuất Excel: " + ex.getMessage());
        }
    }
}
