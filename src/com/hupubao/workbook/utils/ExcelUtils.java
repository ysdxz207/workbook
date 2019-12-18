package com.hupubao.workbook.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExcelUtils {


    /**
     * 设置表头样式 
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getHeadStyle(HSSFWorkbook workbook, boolean columnLast, boolean rowLast) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short) (256*1));
        font.setFontName("宋体");
        font.setBold(true);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        if (columnLast) {
            style.setBorderRight(BorderStyle.MEDIUM);
        }

        if (rowLast) {
            style.setBorderBottom(BorderStyle.MEDIUM);
        }
        return style;
    }

    /**
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short) (256*1.2));
        font.setFontName("宋体");

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setBorderBottom(BorderStyle.MEDIUM);

        return style;
    }

    /**
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getBigTitleStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short) 460);
        font.setFontName("隶书");
        font.setBold(true);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());

        return style;
    }


    /**
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getBigHeadStyle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short) 240);
        font.setFontName("宋体");
        font.setBold(true);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);

        return style;
    }

    /**
     * @param workbook
     * @return
     */
    public static HSSFCellStyle getDataStyle(HSSFWorkbook workbook, boolean columnLast, boolean rowLast) {
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setFontHeight((short) (256*0.8));
        font.setFontName("宋体");

        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        if (columnLast) {
            style.setBorderRight(BorderStyle.MEDIUM);
        }

        if (rowLast) {
            style.setBorderBottom(BorderStyle.MEDIUM);
        }
        return style;
    }

    /**
     * 创建单元格内容
     * @param row
     * @param id
     * @param value
     * @param style
     */
    public static void createCell(HSSFRow row, int column, String value, HSSFCellStyle style) {
        HSSFCell cell = row.createCell(column);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);
        if (style != null) {
            cell.setCellStyle(style);
        }
    }


    /**
     * 创建报表文件
     * @param workbook
     * @param filepath
     * @throws IOException
     */
    public static void createFile(Workbook workbook, String filepath) {
        File outFile = new File(filepath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        try {
            FileOutputStream fOut = new FileOutputStream(filepath);
            workbook.write(fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}