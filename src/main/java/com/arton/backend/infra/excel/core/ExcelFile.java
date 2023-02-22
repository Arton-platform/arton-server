package com.arton.backend.infra.excel.core;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface ExcelFile<T> {
    void write(OutputStream outputStream) throws IOException;
    void addRows(List<T> rows);
}
