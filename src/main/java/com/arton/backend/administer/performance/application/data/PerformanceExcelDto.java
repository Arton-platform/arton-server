package com.arton.backend.administer.performance.application.data;

import com.arton.backend.infra.excel.annotation.DefaultBodyStyle;
import com.arton.backend.infra.excel.annotation.DefaultHeaderStyle;
import com.arton.backend.infra.excel.annotation.ExcelColumn;
import com.arton.backend.infra.excel.annotation.ExcelColumnStyle;
import com.arton.backend.infra.excel.style.DefaultExcelCellStyle;
import com.arton.backend.search.adapter.out.persistence.document.PerformanceDocument;
import com.arton.backend.search.application.data.SearchResultDto;
import lombok.*;

import java.time.format.DateTimeFormatter;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 엑셀 템플릿에 사용할 DTO
 */
@Getter
@NoArgsConstructor
@DefaultHeaderStyle(style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "GREY_HEADER"))
@DefaultBodyStyle(style = @ExcelColumnStyle(excelCellStyleClass = DefaultExcelCellStyle.class, enumName = "BODY"))
@ToString
public class PerformanceExcelDto {
    @ExcelColumn(headerName = "공연번호")
    private Long id;
    @ExcelColumn(headerName = "제목")
    private String title;
    @ExcelColumn(headerName = "장소")
    private String place;
    @ExcelColumn(headerName = "공연일")
    private String startDate;
    @ExcelColumn(headerName = "공연종료일")
    private String endDate;
    @ExcelColumn(headerName = "공연종류")
    private String performanceType;
    @ExcelColumn(headerName = "상태")
    private String showCategory;

    @Builder
    public PerformanceExcelDto(Long id, String title, String place, String startDate, String endDate, String performanceType, String showCategory) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.performanceType = performanceType;
        this.showCategory = showCategory;
    }

    public static PerformanceExcelDto toDtoFromDocument(PerformanceDocument document) {
        return PerformanceExcelDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .place(document.getPlace())
                .startDate(isEmpty(document.getStartDate()) ? null : document.getStartDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .endDate(isEmpty(document.getEndDate()) ? null : document.getEndDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")))
                .performanceType(document.getPerformanceType())
                .showCategory(document.getShowCategory())
                .build();
    }

    // 공연 번호순 정렬
    public static int compareById(PerformanceExcelDto lhs, PerformanceExcelDto rhs) {
            return lhs.id.compareTo(rhs.id);
    }

    // 공연 번호순 정렬 추가
    public static int compareByStartDate(PerformanceExcelDto lhs, PerformanceExcelDto rhs) {
        if (lhs.startDate.equals(rhs.startDate)) {
            return lhs.id.compareTo(rhs.id);
        } else {
            return lhs.startDate.compareTo(rhs.startDate);
        }
    }


}
