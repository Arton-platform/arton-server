package com.arton.backend.infra.excel.style.border;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.ArrayList;
import java.util.List;

public final class DefaultExcelBorders implements ExcelBorders{

    private List<? extends ExcelBorder> borders;

    public DefaultExcelBorders(List<? extends ExcelBorder> borders) {
        validateBorders(borders);
        this.borders = borders;
    }

    public static DefaultExcelBorders newInstance(ExcelBorderStyle excelBorderStyle) {
        List<DefaultExcelBorder> excelBorders = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            excelBorders.add(new DefaultExcelBorder(excelBorderStyle));
        }
        return new DefaultExcelBorders(excelBorders);
    }

    private void validateBorders(List<? extends ExcelBorder> borders) {
        if (borders.size() != 4) {
            throw new CustomException(ErrorCode.EXCEL_BORDER_NOT_VALID.getMessage(), ErrorCode.EXCEL_BORDER_NOT_VALID);
        }
    }

    /**
     * 위 오른 아래 왼 순서
     * @param cellStyle
     */
    @Override
    public void apply(CellStyle cellStyle) {
        borders.get(0).applyTop(cellStyle);
        borders.get(1).applyRight(cellStyle);
        borders.get(2).applyBottom(cellStyle);
        borders.get(3).applyLeft(cellStyle);
    }
}
