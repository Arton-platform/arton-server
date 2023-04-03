package com.arton.backend.infra.excel.core.resource;

import com.arton.backend.infra.excel.annotation.DefaultBodyStyle;
import com.arton.backend.infra.excel.annotation.DefaultHeaderStyle;
import com.arton.backend.infra.excel.annotation.ExcelColumn;
import com.arton.backend.infra.excel.annotation.ExcelColumnStyle;
import com.arton.backend.infra.excel.core.resource.collection.PreCalculatedCellStyleMap;
import com.arton.backend.infra.excel.style.ExcelCellStyle;
import com.arton.backend.infra.excel.style.NoExcelCellStyle;
import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.arton.backend.infra.utils.SuperClassReflectionUtils.getAllFields;
import static com.arton.backend.infra.utils.SuperClassReflectionUtils.getAnnotation;

public final class ExcelRenderResourceFactory {

    public static ExcelRenderResource prepareRenderResource(Class<?> type, Workbook workbook, DataFormatDecider dataFormatDecider) {
        PreCalculatedCellStyleMap styleMap = new PreCalculatedCellStyleMap(dataFormatDecider);
        Map<String, String> headerNamesMap = new LinkedHashMap<>();
        List<String> fieldNames = new ArrayList<>();

        ExcelColumnStyle classDefinedHeaderStyle = getHeaderColumnStyle(type);
        ExcelColumnStyle classDefinedBodyStyle = getBodyColumnStyle(type);

        for (Field field : getAllFields(type)) {
            if (field.isAnnotationPresent(ExcelColumn.class)) {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                styleMap.put(
                        String.class,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.HEADER),
                        getCellStyle(decideAppliedStyleAnnotation(classDefinedHeaderStyle, annotation.headerStyle())), workbook);
                Class<?> fieldType = field.getType();
                styleMap.put(
                        fieldType,
                        ExcelCellKey.of(field.getName(), ExcelRenderLocation.BODY),
                        getCellStyle(decideAppliedStyleAnnotation(classDefinedBodyStyle, annotation.bodyStyle())), workbook);
                fieldNames.add(field.getName());
                headerNamesMap.put(field.getName(), annotation.headerName());
            }
        }

        if (styleMap.isEmpty()) {
            throw new CustomException(ErrorCode.EXCEL_ANNOTATION_NOT_FOUND.getMessage(), ErrorCode.EXCEL_ANNOTATION_NOT_FOUND);
        }
        return new ExcelRenderResource(styleMap, headerNamesMap, fieldNames);
    }

    private static ExcelColumnStyle getHeaderColumnStyle(Class<?> clazz) {
        Annotation annotation = getAnnotation(clazz, DefaultHeaderStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((DefaultHeaderStyle) annotation).style();
    }

    private static ExcelColumnStyle getBodyColumnStyle(Class<?> clazz) {
        Annotation annotation = getAnnotation(clazz, DefaultBodyStyle.class);
        if (annotation == null) {
            return null;
        }
        return ((DefaultBodyStyle) annotation).style();
    }

    private static ExcelColumnStyle decideAppliedStyleAnnotation(ExcelColumnStyle classAnnotation,
                                                                 ExcelColumnStyle fieldAnnotation) {
        if (fieldAnnotation.excelCellStyleClass().equals(NoExcelCellStyle.class) && classAnnotation != null) {
            return classAnnotation;
        }
        return fieldAnnotation;
    }

    private static ExcelCellStyle getCellStyle(ExcelColumnStyle excelColumnStyle) {
        Class<? extends ExcelCellStyle> excelCellStyleClass = excelColumnStyle.excelCellStyleClass();
        // check enum
        if (excelCellStyleClass.isEnum()) {
            String enumName = excelColumnStyle.enumName();
            return findExcelCellStyle(excelCellStyleClass, enumName);
        }

        // check class
        try {
            return excelCellStyleClass.newInstance();
        } catch (InstantiationException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.EXCEL_CELL_STYLE_INVALID.getMessage(), ErrorCode.EXCEL_CELL_STYLE_INVALID);
        } catch (IllegalAccessException e) {
            throw new CustomException(ErrorCode.EXCEL_CELL_STYLE_INVALID.getMessage(), ErrorCode.EXCEL_CELL_STYLE_INVALID);
        }
    }

    private static ExcelCellStyle findExcelCellStyle(Class<?> excelCellStyles, String enumName) {
        try {
            return (ExcelCellStyle) Enum.valueOf((Class<Enum>) excelCellStyles, enumName);
        } catch (NullPointerException e) {
            throw new CustomException(ErrorCode.EXCEL_ENUM_NULL.getMessage(), ErrorCode.EXCEL_ENUM_NULL);
        }
    }

}
