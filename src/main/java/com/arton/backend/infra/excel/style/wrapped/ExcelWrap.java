package com.arton.backend.infra.excel.style.wrapped;

import org.apache.poi.ss.usermodel.CellStyle;

public interface ExcelWrap {
    void apply(CellStyle cellStyle);
}
