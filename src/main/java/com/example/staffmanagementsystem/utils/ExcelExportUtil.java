package com.example.staffmanagementsystem.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ExcelExportUtil {

    public static byte[] exportLeaveReport(
            List<Object[]> sheet1,
            List<Object[]> sheet2
    ) {
        try (Workbook wb = new XSSFWorkbook()) {

            Sheet s1 = wb.createSheet("Tong hop nghi phep");
            Sheet s2 = wb.createSheet("Chi tiet don nghi");

            writeSheet1(s1, wb, sheet1);
            writeSheet2(s2, wb, sheet2);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            wb.write(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Export Excel failed", e);
        }
    }

    private static void writeSheet1(Sheet sheet, Workbook wb, List<Object[]> data) {
        int rowIdx = 0;

        // Style for header
        CellStyle headerStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);

        // Header
        String[] headers = {"Họ tên", "Email", "Phòng ban", "Nghỉ phép năm", "Nghỉ ốm",
                "Nghỉ không lương vượt", "Tổng ngày nghỉ", "Số ngày còn lại"};

        Row headerRow = sheet.createRow(rowIdx++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data
        CellStyle textStyle = wb.createCellStyle();
        textStyle.setWrapText(true);

        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowIdx++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rowData[i] == null ? "" : rowData[i].toString());
                cell.setCellStyle(textStyle);
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private static void writeSheet2(Sheet sheet, Workbook wb, List<Object[]> data) {
        int rowIdx = 0;

        // Style for header
        CellStyle headerStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setWrapText(true);

        String[] headers = {"Mã NV", "Họ tên", "Email", "Phòng ban", "Loại nghỉ",
                "Ngày bắt đầu", "Ngày kết thúc", "Lý do", "Mã đơn"};

        Row headerRow = sheet.createRow(rowIdx++);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Data
        CellStyle textStyle = wb.createCellStyle();
        textStyle.setWrapText(true);

        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowIdx++);
            for (int i = 0; i < rowData.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(rowData[i] == null ? "" : rowData[i].toString());
                cell.setCellStyle(textStyle);
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
