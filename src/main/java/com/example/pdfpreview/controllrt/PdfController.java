package com.example.pdfpreview.controllrt;

import com.example.pdfpreview.util.PdfUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
public class PdfController {
    /**标题字体*/
    private static Font titleFont = new Font(PdfUtils.chineseFont, 35, Font.BOLD, PdfUtils.baseColor);
    /**单元格字体*/
    private static Font cellFont = new Font(PdfUtils.chineseFont, 10, Font.NORMAL, PdfUtils.baseColor);

    @GetMapping("/getPdf")
    public void getPdf(String teacherName, HttpServletResponse response) {
        // 创建Pdf文档对象
        Document doc = new Document(PageSize.A4);
        PdfWriter pdfWriter = null;
        try {
            //指定pdf导出位置，这里我们直接将它导出给response的输出流，如果要导出到磁盘上的话就用FileOutputStream
            pdfWriter = PdfWriter.getInstance(doc, response.getOutputStream());
            doc.open();
            //*******添加第一页内容**********
            Paragraph title = PdfUtils.createCenterParagraph("三年1班学生表", titleFont);
            //设置文本块下间距
            title.setSpacingAfter(80);
            //将文本块加入pdf文档中
            doc.add(title);
            Paragraph teacher = PdfUtils.createRightParagraph("老师：" + teacherName, cellFont);
            doc.add(teacher);
            //******添加第二页内容***********
            //将纸张由竖向改为横向
            Rectangle rectangle = new Rectangle(PageSize.A4);
            doc.setPageSize(rectangle.rotate());
            doc.newPage();
            //创建一个有4列的表
            PdfPTable table = PdfUtils.createCenterTable(4);
            ArrayList<PdfPRow> tableRows = table.getRows();
            //表头
            PdfPCell[] headerCells = new PdfPCell[4];
            headerCells[0] = PdfUtils.createCell("姓名", cellFont);
            headerCells[1] = PdfUtils.createCell("学号", cellFont);
            headerCells[2] = PdfUtils.createCell("住址", cellFont);
            headerCells[3] = PdfUtils.createCell("性别", cellFont);
            PdfPRow headerRow = new PdfPRow(headerCells);
            tableRows.add(headerRow);

            for (int i = 0; i < 5; i++) {
                PdfPCell[] cells = new PdfPCell[4];
                cells[0] = PdfUtils.createCell("学生"+i, cellFont);
                cells[1] = PdfUtils.createCell(""+i, cellFont);
                cells[2] = PdfUtils.createCell("住址"+i, cellFont);
                cells[3] = PdfUtils.createCell(i%2 == 1? "男":"女", cellFont);
                PdfPRow row = new PdfPRow(cells);
                tableRows.add(row);
            }
            doc.add(table);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (doc != null) {
                doc.close();
            }
            if (pdfWriter != null) {
                pdfWriter.close();
            }
        }
    }
}
