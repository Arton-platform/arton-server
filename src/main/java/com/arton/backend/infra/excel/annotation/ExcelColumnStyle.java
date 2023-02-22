package com.arton.backend.infra.excel.annotation;

import com.arton.backend.infra.excel.style.ExcelCellStyle;

public @interface ExcelColumnStyle {
    Class<? extends ExcelCellStyle> excelCellStyleClass();
    String enumName() default "";
}
