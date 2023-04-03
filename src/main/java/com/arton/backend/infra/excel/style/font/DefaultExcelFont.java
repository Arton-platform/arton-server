package com.arton.backend.infra.excel.style.font;

import org.apache.poi.ss.usermodel.Font;

/**
 * 폰트 세팅
 */
public enum DefaultExcelFont implements ExcelFont{

    DEFAULT_HEADER_NOT_BOLD((short)9, false),
    DEFAULT_HEADER_BOLD((short)9, true),
    DEFAULT_BODY_NOT_BOLD((short)11, true),
    DEFAULT_BODY_BOLD((short)11, true),

    ;
    private final short fontSize;
    private final boolean isBold;

    DefaultExcelFont(short fontSize, boolean isBold) {
        this.fontSize = fontSize;
        this.isBold = isBold;
    }

    @Override
    public void apply(Font font) {
        font.setFontHeightInPoints(fontSize); // size
        font.setBold(isBold); // bold
    }

    public short getFontSize() {
        return fontSize;
    }

    public boolean isBold() {
        return isBold;
    }
}
