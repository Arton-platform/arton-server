package com.arton.backend.infra.excel.style.color;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class DefaultExcelColor implements ExcelColor{

    private static final int MIN_RGB = 0;
    private static final int MAX_RGB = 255;

    private byte red;
    private byte green;
    private byte blue;

    public DefaultExcelColor(byte red, byte green, byte blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static DefaultExcelColor rgb(int red, int green, int blue) {
        if (red < MIN_RGB || red > MAX_RGB || green < MIN_RGB ||
                green > MAX_RGB || blue < MIN_RGB || blue > MAX_RGB) {
            throw new CustomException(ErrorCode.PARAMETER_NOT_VALID.getMessage(), ErrorCode.PARAMETER_NOT_VALID);
        }
        return new DefaultExcelColor((byte) red, (byte) green, (byte) blue);
    }

    @Override
    public void applyForeground(CellStyle cellStyle) {
        try {
            XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
            xssfCellStyle.setFillForegroundColor(new XSSFColor(new byte[]{red, green, blue}, new DefaultIndexedColorMap()));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.EXCEL_TYPE_NOT_VALID.getMessage(), ErrorCode.EXCEL_TYPE_NOT_VALID);
        }
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }
}
