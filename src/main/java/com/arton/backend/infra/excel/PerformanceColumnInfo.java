package com.arton.backend.infra.excel;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 페이지에 사용되는 SearchResultDto 를 엑셀로 저장하기 위해
 * 칼럼 정보 세팅
 */
public enum PerformanceColumnInfo implements ExcelColumnInfo {

    id("공연번호", 0, 0, ColumnType.NUMBER),
    imageUrl("썸네일 링크", 0, 1, ColumnType.TEXT),
    title("제목", 0, 2, ColumnType.TEXT),
    place("장소", 0, 3, ColumnType.TEXT),
    startDate("공연일", 0, 4, ColumnType.TEXT),
    endDate("공연종료일", 0, 5, ColumnType.TEXT),
    performanceType("공연타입", 0, 6, ColumnType.TEXT),
    showCategory("게시상태", 0, 7, ColumnType.TEXT);

    private final String text;
    private final int row;
    private final int col;
    private final ColumnType columnType;

    public String getText() {
        return text;
    }

    @Override
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    PerformanceColumnInfo(String text, int row, int col, ColumnType columnType) {
        this.text = text;
        this.row = row;
        this.col = col;
        this.columnType = columnType;
    }

    // row 그룹화해서 header 값 가져오기
    public static Map<Integer, List<PerformanceColumnInfo>> getColumns() {
        return Arrays.stream(PerformanceColumnInfo.values()).collect(Collectors.groupingBy(ExcelColumnInfo::getRow));
    }
}
