package com.example.pdfpreview.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * pdf工具类
 */
@Slf4j
public class PdfUtils {
    public static BaseFont chineseFont;
    public static BaseColor baseColor;
    static {
        try {
            //设置宋体
            chineseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            baseColor = BaseColor.BLACK;
        } catch (DocumentException | IOException e) {
            log.error("设置pdf初始字体异常", e);
        }
    }
    /**
     * 创建一个水平居中的文字段落
     * @param content 内容
     * @param font 字体
     * @return
     */
    public static Paragraph createCenterParagraph(String content, Font font) {
        Paragraph result = new Paragraph(content, font);
        result.setAlignment(Paragraph.ALIGN_CENTER);
        return result;
    }

    /**
     * 创建一个向右对齐的文字段落
     * @param content 内容
     * @param font 字体
     * @return
     */
    public static Paragraph createRightParagraph(String content, Font font) {
        Paragraph result = new Paragraph(content, font);
        result.setAlignment(Paragraph.ALIGN_RIGHT);
        return result;
    }

    /**
     * 创建一个文字段落，对齐方式自定义
     * @param content 内容
     * @param font 字体
     * @param alignment 对齐方式
     * @return
     */
    public static Paragraph createParagraph(String content, Font font, int alignment) {
        Paragraph result = new Paragraph(content, font);
        result.setAlignment(alignment);
        return result;
    }

    /**
     * 创建一个水平居中的表
     * @param numColumns 表的列数
     * @return
     */
    public static PdfPTable createCenterTable(int numColumns) {
        PdfPTable result = new PdfPTable(numColumns);
        result.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        return result;
    }

    /**
     * 创建一个单元格，单元格内容水平居中垂直居中
     * @param content 单元格内容
     * @param font 字体
     * @return
     */
    public static PdfPCell createCell(String content, Font font) {
        PdfPCell result = new PdfPCell(new Phrase(content, font));
        //设置单元格内容垂直居中
        result.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        //设置党员个内容水平居中
        result.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        return result;
    }

    /**
     * 创建一行单元格
     * @param strings 单元格内容集合
     * @param font 字体
     * @return
     */
    public static PdfPCell[] createRowCells(List<String> strings, Font font) {
        PdfPCell[] result = new PdfPCell[strings.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = createCell(strings.get(i), font);
        }
        return result;
    }

    /**
     * 创建表的一行对象
     * @param strings
     * @param font
     * @return
     */
    public static PdfPRow createTableRow(List<String> strings, Font font) {
        PdfPCell[] rowCells = createRowCells(strings, font);
        return new PdfPRow(rowCells);
    }
}
