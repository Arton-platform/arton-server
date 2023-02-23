package com.arton.backend.infra.excel.style.border;

import org.apache.poi.ss.usermodel.CellStyle;

public final class DefaultExcelBorder implements ExcelBorder{
    private ExcelBorderStyle borderStyle;

    public DefaultExcelBorder(ExcelBorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    @Override
    public void applyTop(CellStyle cellStyle) {
        cellStyle.setBorderTop(borderStyle.getBorderStyle());
    }

    @Override
    public void applyRight(CellStyle cellStyle) {
        cellStyle.setBorderRight(borderStyle.getBorderStyle());
    }

    @Override
    public void applyBottom(CellStyle cellStyle) {
        cellStyle.setBorderBottom(borderStyle.getBorderStyle());
    }

    @Override
    public void applyLeft(CellStyle cellStyle) {
        cellStyle.setBorderLeft(borderStyle.getBorderStyle());
    }
}
