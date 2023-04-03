package com.arton.backend.infra.excel.style;

import com.arton.backend.infra.excel.style.config.ExcelCellStyleConfig;
import org.apache.poi.ss.usermodel.CellStyle;

public abstract class CustomExcelCellStyle implements ExcelCellStyle{

    private ExcelCellStyleConfig config = new ExcelCellStyleConfig();

    public CustomExcelCellStyle() {
        configure(config);
    }

    public abstract void configure(ExcelCellStyleConfig config);

    @Override
    public void apply(CellStyle cellStyle) {
        config.configure(cellStyle);
    }
}
