package com.arton.backend.infra.event;

import com.arton.backend.infra.utils.SuperClassReflectionUtils;
import com.arton.backend.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@SpringBootTest
class UserRegisteredEventHandlerTest {

    @Test
    void getFieldNameTest() {
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            System.out.println("name = " + name);
        }
    }

    @Test
    void getFieldNameBySuperClassReflectionUtils() {
        List<Field> allFields = SuperClassReflectionUtils.getAllFields(User.class);
        for (Field allField : allFields) {
            String name = allField.getName();
            System.out.println("allField = " + name);
            String method = "get "+name;
            String lowerCamelCase = toLowerCamelCase(method);
            System.out.println("lowerCamelCase = " + lowerCamelCase);

        }
    }

    @Test
    void getAllMethodTest() {
        List<Method> allMethods = SuperClassReflectionUtils.getAllMethods(User.class);
        for (Method allMethod : allMethods) {
            System.out.println("allMethod = " + allMethod.getName());
        }
    }

    String toLowerCamelCase(String text) {
        String[] words = text.split("[\\W_]+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }
}