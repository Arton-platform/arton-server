package com.arton.backend.infra.excel.core;

import com.arton.backend.infra.excel.core.resource.*;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

import static com.arton.backend.infra.excel.utils.SuperClassReflectionUtils.getField;

public abstract class SXSSFExcelFile<T> implements ExcelFile<T>{

    protected static final SpreadsheetVersion SPREADSHEET_VERSION = SpreadsheetVersion.EXCEL2007;

    protected SXSSFWorkbook wb;
    protected Sheet sheet;
    protected ExcelRenderResource resource;

    public SXSSFExcelFile(Class<T> type) {
        this(Collections.emptyList(), type, new DefaultDataFormatDecider());
    }

    public SXSSFExcelFile(List<T> rows, Class<T> type) {
        this(rows, type, new DefaultDataFormatDecider());
    }

    public SXSSFExcelFile(List<T> rows, Class<T> type, DataFormatDecider dataFormatDecider) {
        validateData(rows);
        this.wb = new SXSSFWorkbook();
        this.resource = ExcelRenderResourceFactory.prepareRenderResource(type, wb, dataFormatDecider);
        addRows(rows);
    }

    protected void validateData(List<T> data) { }

    protected abstract void renderExcel(List<T> data);

    @Override
    public void write(OutputStream outputStream) throws IOException {
        wb.write(outputStream);
        wb.close();
        wb.dispose();
        outputStream.close();
    }

    protected void renderHeadersWithNewSheet(Sheet sheet, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;
        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.HEADER));
            cell.setCellValue(resource.getHeaderName(dataFieldName));
        }
    }

    protected void renderBody(Object data, int rowIndex, int columnStartIndex) {
        Row row = sheet.createRow(rowIndex);
        int columnIndex = columnStartIndex;
        for (String dataFieldName : resource.getDataFieldNames()) {
            Cell cell = row.createCell(columnIndex++);
            try {
                Field field = getField(data.getClass(), dataFieldName);
                field.setAccessible(true);
                cell.setCellStyle(resource.getCellStyle(dataFieldName, ExcelRenderLocation.BODY));
                Object cellValue = field.get(data);
                renderCellValue(cell, cellValue);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.EXCEL_INTERNAL_ERROR.getMessage(), ErrorCode.EXCEL_INTERNAL_ERROR);
            }
        }
    }

    private void renderCellValue(Cell cell, Object cellValue) {
        if (cellValue instanceof Number) {
            Number number = (Number) cellValue;
            cell.setCellValue(number.doubleValue());
            return;
        }
        cell.setCellValue(cellValue == null ? "" : cellValue.toString());
    }

}
