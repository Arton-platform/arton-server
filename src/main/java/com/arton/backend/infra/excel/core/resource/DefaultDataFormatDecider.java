package com.arton.backend.infra.excel.core.resource;

import org.apache.poi.ss.usermodel.DataFormat;

import java.util.Arrays;
import java.util.List;

/**
 * 데이터 포맷 세팅
 */
public class DefaultDataFormatDecider implements DataFormatDecider{

    private static final String NUMBER = "#,##0";
    private static final String FLOAT_WITH_2_POINTS = "#,##0.00";
    private static final String DEFAULT = "";

    @Override
    public short getDataFormat(DataFormat dataFormat, Class<?> type) {
        if (isFloatType(type)) {
            return dataFormat.getFormat(FLOAT_WITH_2_POINTS);
        }
        if (isIntegerType(type)) {
            return dataFormat.getFormat(NUMBER);
        }
        return dataFormat.getFormat(DEFAULT);
    }

    private boolean isFloatType(Class<?> type) {
        List<Class<?>> classes = Arrays.asList(Float.class, float.class, Double.class, double.class);
        return classes.contains(type);
    }

    private boolean isIntegerType(Class<?> type) {
        List<Class<?>> classes = Arrays.asList(Byte.class, byte.class, Short.class, short.class, Integer.class, int.class, Long.class, long.class);
        return classes.contains(type);
    }
}
