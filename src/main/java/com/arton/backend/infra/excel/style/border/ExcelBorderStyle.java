package com.arton.backend.infra.excel.style.border;

import org.apache.poi.ss.usermodel.BorderStyle;

public enum ExcelBorderStyle {
    NONE(BorderStyle.NONE),
    THIN(BorderStyle.THIN),
    MEDIUM(BorderStyle.MEDIUM);
    ;

    private final BorderStyle borderStyle;

    ExcelBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }
}
