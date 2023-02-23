package com.arton.backend.infra.excel.core.resource;

import org.apache.poi.ss.usermodel.DataFormat;

public interface DataFormatDecider {
    short getDataFormat(DataFormat dataFormat, Class<?> type);
}
