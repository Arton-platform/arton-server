package com.arton.backend.infra.utils;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SuperClassReflectionUtils {

    private SuperClassReflectionUtils() {
    }

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> allClassesIncludingSuperClass : getAllClassesIncludingSuperClasses(clazz, true)) {
            fields.addAll(Arrays.asList(allClassesIncludingSuperClass.getDeclaredFields()));
        }
        return fields;
    }

    public static List<Method> getAllMethods(Class<?> clazz) {
        List<Method> fields = new ArrayList<>();
        for (Class<?> allClassesIncludingSuperClass : getAllClassesIncludingSuperClasses(clazz, true)) {
            fields.addAll(Arrays.asList(allClassesIncludingSuperClass.getDeclaredMethods()));
        }
        return fields;
    }

    public static List<Method> getOnlyClassMethods(Class<?> clazz) {
        List<Method> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        return fields;
    }

    public static Annotation getAnnotation(Class<?> clazz, Class<? extends Annotation> targetAnnotation) {
        for (Class<?> allClassesIncludingSuperClass : getAllClassesIncludingSuperClasses(clazz, false)) {
            if (allClassesIncludingSuperClass.isAnnotationPresent(targetAnnotation)) {
                return allClassesIncludingSuperClass.getAnnotation(targetAnnotation);
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String name) {
        for (Class<?> allClassesIncludingSuperClass : getAllClassesIncludingSuperClasses(clazz, false)) {
            for (Field declaredField : allClassesIncludingSuperClass.getDeclaredFields()) {
                if (declaredField.getName().equals(name)) {
                    try {
                        return allClassesIncludingSuperClass.getDeclaredField(name);
                    } catch (NoSuchFieldException e) {
                        throw new CustomException(ErrorCode.EXCEL_FIELD_NOT_FOUND.getMessage(), ErrorCode.EXCEL_FIELD_NOT_FOUND);
                    }
                }
            }
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String name) {
        for (Class<?> allClassesIncludingSuperClass : getAllClassesIncludingSuperClasses(clazz, false)) {
            for (Field declaredField : allClassesIncludingSuperClass.getDeclaredFields()) {
                if (declaredField.getName().equals(name)) {
                    try {
                        return allClassesIncludingSuperClass.getMethod(name);
                    }catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static List<Class<?>> getAllClassesIncludingSuperClasses(Class<?> clazz, boolean fromSuper) {
        List<Class<?>> classes = new ArrayList<>();
        while (clazz != null) {
            classes.add(clazz);
            clazz = clazz.getSuperclass();
        }
        if (fromSuper) {
            Collections.reverse(classes);
        }
        return classes;
    }
}
