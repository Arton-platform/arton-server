package com.arton.backend.infra.excel.style;

import com.arton.backend.infra.excel.style.align.DefaultExcelAlign;
import com.arton.backend.infra.excel.style.align.ExcelAlign;
import com.arton.backend.infra.excel.style.border.DefaultExcelBorders;
import com.arton.backend.infra.excel.style.border.ExcelBorderStyle;
import com.arton.backend.infra.excel.style.color.DefaultExcelColor;
import com.arton.backend.infra.excel.style.color.ExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

public enum DefaultExcelCellStyle implements ExcelCellStyle {

    GREY_HEADER(DefaultExcelColor.rgb(217, 217, 217),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN),
            DefaultExcelAlign.CENTER_SELECTION_CENTER),
    BODY(DefaultExcelColor.rgb(255, 255, 255),
            DefaultExcelBorders.newInstance(ExcelBorderStyle.THIN),
            DefaultExcelAlign.CENTER_SELECTION_CENTER);

    private final ExcelColor color;
    private final DefaultExcelBorders borders;
    private final ExcelAlign excelAlign;

    DefaultExcelCellStyle(ExcelColor color, DefaultExcelBorders borders, ExcelAlign excelAlign) {
        this.color = color;
        this.borders = borders;
        this.excelAlign = excelAlign;
    }

    @Override
    public void apply(CellStyle cellStyle) {
        color.applyForeground(cellStyle);
        borders.apply(cellStyle);
        excelAlign.apply(cellStyle);
    }
}
