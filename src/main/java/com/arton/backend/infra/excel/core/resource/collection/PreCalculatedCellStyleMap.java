package com.arton.backend.infra.excel.core.resource.collection;

import com.arton.backend.infra.excel.core.resource.DataFormatDecider;
import com.arton.backend.infra.excel.core.resource.ExcelCellKey;
import com.arton.backend.infra.excel.style.ExcelCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class PreCalculatedCellStyleMap {

    private final DataFormatDecider dataFormatDecider;

    public PreCalculatedCellStyleMap(DataFormatDecider dataFormatDecider) {
        this.dataFormatDecider = dataFormatDecider;
    }

    private final Map<ExcelCellKey, CellStyle> cellStyleMap = new HashMap<>();

    public void put(Class<?> fieldType, ExcelCellKey excelCellKey, ExcelCellStyle excelCellStyle, Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormatDecider.getDataFormat(dataFormat, fieldType));
        excelCellStyle.apply(cellStyle);
        cellStyleMap.put(excelCellKey, cellStyle);
    }

    public CellStyle get(ExcelCellKey excelCellKey) {
        return cellStyleMap.get(excelCellKey);
    }

    public boolean isEmpty() {
        return cellStyleMap.isEmpty();
    }
}
