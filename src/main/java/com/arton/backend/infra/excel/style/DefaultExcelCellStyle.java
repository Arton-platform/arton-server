package com.arton.backend.infra.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

public enum DefaultExcelCellStyle implements ExcelCellStyle {

    @Override
    public void apply(CellStyle cellStyle) {
        // 기본으로 두기
    }
}
