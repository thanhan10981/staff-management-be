package com.example.staffmanagementsystem.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFExportUtil {

    public static byte[] exportLeaveReportPDF(List<Object[]> sheet1, List<Object[]> sheet2) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

            Paragraph title1 = new Paragraph("Tổng hợp nghỉ phép", titleFont);
            title1.setSpacingAfter(20f);
            document.add(title1);

            PdfPTable table1 = new PdfPTable(8);
            table1.setWidthPercentage(100);
            table1.setWidths(new float[]{2f, 3f, 2f, 1.5f, 1.5f, 2f, 1.5f, 1.5f});
            addTableHeader(table1, new String[]{
                    "Họ tên","Email","Phòng ban","Nghỉ phép năm",
                    "Nghỉ ốm","Nghỉ không lương vượt","Tổng ngày nghỉ","Số ngày còn lại"
            });
            addTableData(table1, sheet1);
            document.add(table1);

            document.add(new Paragraph("\n"));

            Paragraph title2 = new Paragraph("Chi tiết đơn nghỉ", titleFont);
            title2.setSpacingAfter(20f);
            document.add(title2);

            PdfPTable table2 = new PdfPTable(9);
            table2.setWidthPercentage(100);
            table2.setWidths(new float[]{1.5f, 2f, 3f, 2f, 1.5f, 1.5f, 1.5f, 2f, 1.5f});
            addTableHeader(table2, new String[]{
                    "Mã NV","Họ tên","Email","Phòng ban","Loại nghỉ",
                    "Ngày bắt đầu","Ngày kết thúc","Lý do","Mã đơn"
            });
            addTableData(table2, sheet2);
            document.add(table2);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Export PDF failed", e);
        }
    }

    private static void addTableHeader(PdfPTable table, String[] headers) {
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        for (String h : headers) {
            table.addCell(new Phrase(h, headerFont));
        }
    }

    private static void addTableData(PdfPTable table, List<Object[]> data) {
        Font dataFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);
        for (Object[] row : data) {
            for (Object cellData : row) {
                table.addCell(new Phrase(cellData == null ? "" : cellData.toString(), dataFont));
            }
        }
    }
}

