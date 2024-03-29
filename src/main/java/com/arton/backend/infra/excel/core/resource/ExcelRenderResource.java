package com.arton.backend.infra.excel.core.resource;

import com.arton.backend.infra.excel.core.resource.collection.PreCalculatedCellStyleMap;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;
import java.util.Map;

public class ExcelRenderResource {

    private PreCalculatedCellStyleMap styleMap;
    private Map<String, String> excelHeaderNames;
    private List<String> dataFieldNames;

    public ExcelRenderResource(PreCalculatedCellStyleMap styleMap, Map<String, String> excelHeaderNames, List<String> dataFieldNames) {
        this.styleMap = styleMap;
        this.excelHeaderNames = excelHeaderNames;
        this.dataFieldNames = dataFieldNames;
    }

    public CellStyle getCellStyle(String dataFieldName, ExcelRenderLocation excelRenderLocation) {
        return styleMap.get(ExcelCellKey.of(dataFieldName, excelRenderLocation));
    }

    public String getHeaderName(String dataFieldName) {
        return excelHeaderNames.get(dataFieldName);
    }

    public List<String> getDataFieldNames() {
        return dataFieldNames;
    }
}
