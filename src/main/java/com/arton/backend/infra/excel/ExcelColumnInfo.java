package com.arton.backend.infra.excel;

public interface ExcelColumnInfo {
    public static final int headerRow = 0;
    public default int getRow() { return headerRow;}
}
