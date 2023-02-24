package com.arton.backend.infra.excel.style.font;

import org.apache.poi.ss.usermodel.Font;

public interface ExcelFont {
    void apply(Font font);
}
