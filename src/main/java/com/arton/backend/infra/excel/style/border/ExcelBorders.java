package com.arton.backend.infra.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelBorders {
    void apply(CellStyle cellStyle);
}
