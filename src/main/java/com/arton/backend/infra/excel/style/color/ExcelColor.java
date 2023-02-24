package com.arton.backend.infra.excel.style.color;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 색 지정
 */
public interface ExcelColor {
    void applyForeground(CellStyle cellStyle);
}
