package com.arton.backend.infra.excel.style.align;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public enum DefaultExcelAlign implements ExcelAlign{
    /** 가운데 가운데 정렬 */
    CENTER_SELECTION_CENTER(HorizontalAlignment.CENTER_SELECTION, VerticalAlignment.CENTER);

    private final HorizontalAlignment horizontalAlignment;
    private final VerticalAlignment verticalAlignment;

    DefaultExcelAlign(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    @Override
    public void apply(CellStyle cellStyle) {
        cellStyle.setAlignment(horizontalAlignment);
        cellStyle.setVerticalAlignment(verticalAlignment);
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }
}
