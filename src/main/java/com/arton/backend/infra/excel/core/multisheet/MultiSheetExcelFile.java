package com.arton.backend.infra.excel.core.multisheet;

import com.arton.backend.infra.excel.core.SXSSFExcelFile;
import com.arton.backend.infra.excel.core.resource.DataFormatDecider;
import org.apache.commons.compress.archivers.zip.Zip64Mode;

import java.util.List;

public final class MultiSheetExcelFile<T> extends SXSSFExcelFile<T> {

    private static final int maxRows = 5000;
    private static final int ROW_START_IDX = 0;
    private static final int COL_START_IDX = 0;
    private int currentIDX = ROW_START_IDX;

    public MultiSheetExcelFile(Class<T> type) {
        super(type);
        wb.setZip64Mode(Zip64Mode.Always);
    }

    public MultiSheetExcelFile(List<T> rows, Class<T> type) {
        super(rows, type);
        wb.setZip64Mode(Zip64Mode.Always);
    }

    public MultiSheetExcelFile(List<T> data, Class<T> type, DataFormatDecider dataFormatDecider) {
        super(data, type, dataFormatDecider);
        wb.setZip64Mode(Zip64Mode.Always);
    }

    @Override
    protected void renderExcel(List<T> data) {
        if (data.isEmpty()) {
            createNewSheetWithHeader();
            return;
        }
        createNewSheetWithHeader();
        addRows(data);
    }

    @Override
    public void addRows(List<T> rows) {
        for (Object row : rows) {
            renderBody(row, currentIDX++, COL_START_IDX);
            if (currentIDX == maxRows) {
                // 다음 sheet 헤더를 위해 초기화
                currentIDX = 0;
                createNewSheetWithHeader();
            }
        }
    }

    private void createNewSheetWithHeader(){
        sheet = wb.createSheet();
        renderHeadersWithNewSheet(sheet, ROW_START_IDX, COL_START_IDX);
        currentIDX++;
    }


}
