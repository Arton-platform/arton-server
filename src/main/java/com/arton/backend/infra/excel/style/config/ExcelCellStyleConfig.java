package com.arton.backend.infra.excel.style.config;

import com.arton.backend.infra.excel.style.align.ExcelAlign;
import com.arton.backend.infra.excel.style.align.NoExcelAlign;
import com.arton.backend.infra.excel.style.border.ExcelBorders;
import com.arton.backend.infra.excel.style.border.NoExcelBorders;
import com.arton.backend.infra.excel.style.color.DefaultExcelColor;
import com.arton.backend.infra.excel.style.color.ExcelColor;
import com.arton.backend.infra.excel.style.color.NoExcelColor;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelCellStyleConfig {

    private ExcelAlign excelAlign = new NoExcelAlign();
    private ExcelColor foregroundColor = new NoExcelColor();
    private ExcelBorders excelBorders = new NoExcelBorders();

    public ExcelCellStyleConfig() {
    }

    public ExcelCellStyleConfig excelAlign(ExcelAlign excelAlign) {
        this.excelAlign = excelAlign;
        return this;
    }

    public ExcelCellStyleConfig foregroundColor(int red, int green, int blue) {
        this.foregroundColor = DefaultExcelColor.rgb(red, green, blue);
        return this;
    }

    public ExcelCellStyleConfig excelBorders(ExcelBorders excelBorders) {
        this.excelBorders = excelBorders;
        return this;
    }

    public void configure(CellStyle cellStyle) {
        excelAlign.apply(cellStyle);
        foregroundColor.applyForeground(cellStyle);
        excelBorders.apply(cellStyle);
    }
}
