package com.arton.backend.infra.utils;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * 메일 템플릿 관련 유틸
 */
public final class MailTemplateUtils {

    public static String replaceTemplateBody(String content, Object obj) {
        List<String> fields = SuperClassReflectionUtils.getAllFields(obj.getClass()).stream().map(Field::getName).collect(Collectors.toList());
        List<String> methods = SuperClassReflectionUtils.getOnlyClassMethods(obj.getClass()).stream().map(Method::getName).collect(Collectors.toList());
        Class<?> classObj = obj.getClass();
        for (String field : fields) {
            String old = "${" + field + "}";
            if (content.contains(old)) {
                for (String method : methods) {
                    if (method.toLowerCase(Locale.ROOT).equals(("get" + field).toLowerCase(Locale.ROOT))) {
                        try {
                            Method declaredMethod = classObj.getDeclaredMethod(method);
                            String value = (String)declaredMethod.invoke(obj);
                            content = content.replace(old, value);
                        } catch (Exception e) {
                            throw new CustomException(ErrorCode.AUTO_MAIL_ERROR.getMessage(), ErrorCode.AUTO_MAIL_ERROR);
                        }
                    }
                }
            }
        }
        return content;
    }
}
