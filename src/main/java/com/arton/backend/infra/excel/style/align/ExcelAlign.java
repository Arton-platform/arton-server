package com.arton.backend.infra.excel.style.align;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelAlign {
    void apply(CellStyle cellStyle);
}
